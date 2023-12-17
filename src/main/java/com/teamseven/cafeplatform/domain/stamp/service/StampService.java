package com.teamseven.cafeplatform.domain.stamp.service;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.MenuRepository;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.stamp.common.BoardState;
import com.teamseven.cafeplatform.domain.stamp.common.TotalStamp;
import com.teamseven.cafeplatform.domain.stamp.dto.StampSettingDTO;
import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import com.teamseven.cafeplatform.domain.stamp.entity.StampGift;
import com.teamseven.cafeplatform.domain.stamp.entity.StampHistory;
import com.teamseven.cafeplatform.domain.stamp.entity.StampSetting;
import com.teamseven.cafeplatform.domain.stamp.repository.StampBoardRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampGiftRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampHistoryRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampSettingRepository;
import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import com.teamseven.cafeplatform.domain.user.repository.CafeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StampService {
    private final StampBoardRepository stampBoardRepository;
    private final StampGiftRepository stampGiftRepository;
    private final StampHistoryRepository stampHistoryRepository;
    private final StampSettingRepository stampSettingRepository;
    private final CafeRepository cafeRepository;
    private final MenuRepository menuRepository;
    private final CafeMemberRepository cafeMemberRepository;

    public StampSetting setStampSetting(StampSettingDTO dto) {
        Cafe cafe;
        Menu giftFirst;
        Menu giftSecond;
        try {
            cafe = cafeRepository.findById(dto.getCafeId()).orElseThrow();
            giftFirst = menuRepository.findById(dto.getGiftFirstId()).orElseThrow();
            giftSecond = menuRepository.findById(dto.getGiftSecondId()).orElseThrow();
        } catch (Exception e) {
            return null;
        }
        return stampSettingRepository.save(dto.toEntity(cafe, giftFirst, giftSecond));
    }

    public StampSetting getRecentSetting(Long cafeId) {
        return stampSettingRepository.findFirstByCafeIdOrderByCreateTimeDesc(cafeId).orElse(null);
    }


    public StampBoard makeStampBoard(Long userId, Long cafeId) {
        Optional<CafeMember> cafeMember = cafeMemberRepository.findByUserIdAndCafeId(userId, cafeId);
        StampSetting stampSetting = getRecentSetting(cafeId);
        if (cafeMember.isEmpty() || stampSetting == null) return null;
        Optional<StampBoard> optionalStampBoard = stampBoardRepository.findFirstByCafeMemberOrderByCreateTimeDesc(cafeMember.get());
        LocalDate expirationDate;
        if(optionalStampBoard.isPresent()){
            if(optionalStampBoard.get().getState().equals(BoardState.ACTIVE)){
                return optionalStampBoard.get();
            }else {
                if(LocalDate.now().isBefore(optionalStampBoard.get().getExpirationDate())){
                expirationDate = optionalStampBoard.get().getExpirationDate().plusMonths(stampSetting.getExpiration().getMonth());
                }else {
                    expirationDate = LocalDate.now().plusMonths(stampSetting.getExpiration().getMonth());
                }
            }
        }else {
            expirationDate = LocalDate.now().plusMonths(stampSetting.getExpiration().getMonth());
        }
        return stampBoardRepository.save(StampBoard.builder()
                .cafeMember(cafeMember.get())
                .stampSetting(stampSetting)
                .state(BoardState.ACTIVE)
                .expirationDate(expirationDate)
                .accruedStamp(0)
                .build());
    }

    public List<StampBoard> getAllUsersStampBoard(Long userId) {
        return stampBoardRepository.findAllByUserId(userId, BoardState.ACTIVE);
    }

    public StampBoard getUsersCafeStampBoard(Long userId, Long cafeId) {
        return stampBoardRepository.findByUserIdAnAndCafeId(userId, cafeId, BoardState.ACTIVE).orElse(null);
    }

    @Transactional
    public StampHistory earnStamp(Long userId, Long cafeId, Order order, int accrual, boolean eventBirthday, boolean eventFirst) {
        StampBoard stampBoard = getUsersCafeStampBoard(userId, cafeId); //활성화 상태의 보드 불러옴
        if (stampBoard == null) stampBoard = makeStampBoard(userId, cafeId);    //없으면 하나 만듬
        if (stampBoard.getExpirationDate().isAfter(LocalDate.now())) stampBoard = makeStampBoard(userId, cafeId);   //만료일 체크해보고 지났으면 새로만듬
        stampBoard.setAccruedStamp(stampBoard.getAccruedStamp() + accrual); //보드에 적립된스탬프 추가

        if(stampBoard.getStampSetting().getTotalStamp().equals(TotalStamp.TEN)) {   //총개수 10개일경우
            if (stampBoard.getAccruedStamp() == 10) {   //10개달성 - 보상주고 상태 achieved 로 변경
                makeStampGift(stampBoard, stampBoard.getStampSetting().getGiftFirst());
                stampBoard.setState(BoardState.ACHIEVED);
            }
        }else { //총개수 20개일경우
            if (stampBoard.getAccruedStamp() == 10) { //10개 달서 - 보상줌
                makeStampGift(stampBoard, stampBoard.getStampSetting().getGiftFirst());
            } else if (stampBoard.getAccruedStamp() == 20) {    //20개 달성 - 보사주고 상태 achieved 로 변경
                makeStampGift(stampBoard, stampBoard.getStampSetting().getGiftSecond());
                stampBoard.setState(BoardState.ACHIEVED);
            }
        }
        stampBoardRepository.save(stampBoard);
        return stampHistoryRepository.save(StampHistory.builder()
                .stampBoard(stampBoard)
                .accrualStamp(accrual)
                .eventBirthday(eventBirthday)
                .eventFirst(eventFirst)
                .build());
    }

    public StampGift makeStampGift(StampBoard stampBoard, Menu menu) {
        return StampGift.builder()
                .stampBoard(stampBoard)
                .menu(menu)
                .used(false)
                .build();
    }

    @Transactional
    public StampGift useStampGift(StampGift stampGift) {
        stampGift.setUsed(true);
        stampGift.setUsedTime(LocalDateTime.now());
        return stampGiftRepository.save(stampGift);
    }

    public StampGift getStampGiftById(long id) {
        return stampGiftRepository.findById(id).orElse(null);
    }

    public List<StampGift> getAllStampGiftByUser(Long userId){
        return stampGiftRepository.findAllByStampBoardCafeMemberUserIdAndUsed(userId, false);
    }

    public List<StampGift> getAllStampGiftByUserAndCafe(Long userId, Long cafeId) {
        return stampGiftRepository.findAllByUserAndCafe(userId, cafeId);
    }
}
