package com.teamseven.cafeplatform.domain.stamp.service;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.MenuRepository;
import com.teamseven.cafeplatform.domain.stamp.common.BoardState;
import com.teamseven.cafeplatform.domain.stamp.dto.StampSettingDTO;
import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import com.teamseven.cafeplatform.domain.stamp.entity.StampGift;
import com.teamseven.cafeplatform.domain.stamp.entity.StampSetting;
import com.teamseven.cafeplatform.domain.stamp.repository.StampBoardRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampGiftRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampHistoryRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampSettingRepository;
import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import com.teamseven.cafeplatform.domain.user.repository.CafeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public StampBoard makeNewStampBoard(Long userId, Long cafeId) {
        Optional<CafeMember> cafeMember = cafeMemberRepository.findByUserIdAndCafeId(userId, cafeId);
        StampSetting stampSetting = getRecentSetting(cafeId);
        if (cafeMember.isEmpty() || stampSetting == null) return null;
        return stampBoardRepository.save(StampBoard.builder()
                .cafeMember(cafeMember.get())
                .stampSetting(stampSetting)
                .state(BoardState.ACTIVE)
                .expirationDate(LocalDate.now().plusMonths(stampSetting.getExpiration().getMonth()))
                .accruedStamp(0)
                .build());
    }

    public StampGift getStampGiftById(long id) {
        return stampGiftRepository.findById(id).orElse(null);
    }
}
