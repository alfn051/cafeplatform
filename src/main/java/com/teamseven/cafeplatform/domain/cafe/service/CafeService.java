package com.teamseven.cafeplatform.domain.cafe.service;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.config.KakaoLocalApiHelper;
import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeThemeDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.MenuCreateDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.cafe.entity.MonthlyRecord;
import com.teamseven.cafeplatform.domain.cafe.entity.Partnership;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.MonthlyRecordRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.PartnershipRepository;
import com.teamseven.cafeplatform.domain.propensity.dto.CafePropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.entity.CafePropensity;
import com.teamseven.cafeplatform.domain.propensity.entity.UserPropensity;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import com.teamseven.cafeplatform.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeService {
    private final CafeRepository cafeRepository;
    private final PartnershipRepository partnershipRepository;
    private final UserService userService;
    private final FileService fileService;
    private final MenuService menuService;
    private final PropensityService propensityService;
    private final MonthlyRecordRepository monthlyRecordRepository;
    private final KakaoLocalApiHelper kakaoLocalApiHelper;
    private final StampService stampService;

    @Value("${default_color}")
    private String defaultColor;
    private final int distanceRange = 1000;
    private final StampImage defaultStampImage = StampImage.A;

    @Transactional
    public Cafe registerCafe(CafeRegistrationDTO dto) {
        User owner = userService.getUserById(dto.getOwnerId());
        if (owner == null) return null;

        DirectionDTO direction;
        try {
            direction = kakaoLocalApiHelper.getDirectionByKeyword(dto.getBaseAddress());
        } catch (Exception e) {
            log.error("주소 api에서 위도 경도 가져오는데 오류가 발생함: " + e.getMessage());
            direction = DirectionDTO.builder().longitude(129.07506783124393).latitude(35.17973748292069).build();
        }

        Cafe cafe = cafeRepository.save(dto.toEntity(owner, defaultColor, defaultStampImage, direction, CafeState.ACTIVE));
        partnership(cafe.getId(), true);
        if (dto.getPhoto() != null) {
            try { //사진 파일 저장
                fileService.storeCafePhotos(dto.getPhotoList(), cafe);
            } catch (IOException e) {
                log.error("카페 등록 사진 파일 저장 실패" + e.getMessage());
            }
        }
        return cafe;
    }

    @Transactional
    public Cafe setTheme(CafeThemeDTO dto) {
        Optional<Cafe> optionalCafe = cafeRepository.findById(dto.getCafeId());
        if (optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        cafe.setColor(dto.getColor());
        cafe.setStampImage(dto.getStampImage());
        return cafeRepository.save(cafe);
    }

    @Transactional
    public CafePropensity setPropensity(CafePropensityDTO dto) {
        Optional<Cafe> optionalCafe = cafeRepository.findById(dto.getCafeId());
        if (optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        return propensityService.setCafePropensity(dto, cafe);
    }


    @Transactional
    public Menu addMenu(MenuCreateDTO dto) {
        Optional<Cafe> optionalCafe = cafeRepository.findById(dto.getCafeId());
        if (optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        return menuService.createMenu(dto, cafe);
    }

    public Partnership partnership(Long cafeId, boolean freeTrial) {
        Cafe cafe = getCafeById(cafeId);
        if(cafe == null) return null;
        Optional<Partnership> optionalPartnership = partnershipRepository.findFirstByCafeOrderByEndDateDesc(cafe);
        Partnership partnership;
        if(optionalPartnership.isPresent()){
            if(optionalPartnership.get().getEndDate().isAfter(LocalDate.now())){
                partnership = Partnership.builder()
                        .cafe(cafe)
                        .startDate(optionalPartnership.get().getEndDate().plusDays(1))
                        .endDate(optionalPartnership.get().getEndDate().plusMonths(1))
                        .freeTrial(false)
                        .viewCount(0L)
                        .build();
            }else {
                partnership = Partnership.builder()
                        .cafe(cafe)
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusMonths(1))
                        .freeTrial(false)
                        .viewCount(0L)
                        .build();
            }
        }else {
            partnership = Partnership.builder()
                    .cafe(cafe)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusMonths(1))
                    .freeTrial(true)
                    .viewCount(0L)
                    .build();
        }
        cafe.setPartnerState(true);
        return partnershipRepository.save(partnership);
    }

    public Partnership checkPartnership(Long cafeId) {
        Cafe cafe = getCafeById(cafeId);
        if(cafe == null) return null;
        Optional<Partnership> optionalPartnership = partnershipRepository.findFirstByCafeOrderByEndDateDesc(cafe);
        if(optionalPartnership.isEmpty()){
            cafe.setPartnerState(false);
            return null;
        }
        Partnership partnership = optionalPartnership.get();
        if(partnership.getEndDate().isBefore(LocalDate.now())){
            cafe.setPartnerState(false);
        }
        return partnership;
    }

    public MonthlyRecord makeMonthlyRecord(Long cafeId) {
        Cafe cafe = getCafeById(cafeId);
        if(cafe == null) return null;
        return monthlyRecordRepository.save(MonthlyRecord.builder()
                .cafe(cafe)
                .month(LocalDate.now())
                .amount(0)
                .stampAmount(0)
                .pointAmount(0)
                .totalAmount(0)
                .build());
    }

    public MonthlyRecord recordMonthlyRecord(Long cafeId, Long amount, Long stamp, Long point, Long total) {
        Optional<MonthlyRecord> optionalMonthlyRecord = monthlyRecordRepository.findFirstByCafeIdOrderByMonthDesc(cafeId);
        MonthlyRecord monthlyRecord;
        if (optionalMonthlyRecord.isPresent()){
            if (optionalMonthlyRecord.get().getMonth().getMonth() == LocalDate.now().getMonth()){
                monthlyRecord = optionalMonthlyRecord.get();
            }else {
                monthlyRecord = makeMonthlyRecord(cafeId);
            }
        }else {
            monthlyRecord = makeMonthlyRecord(cafeId);
        }
        monthlyRecord.setAmount(monthlyRecord.getAmount()+amount);
        monthlyRecord.setStampAmount(monthlyRecord.getStampAmount()+stamp);
        monthlyRecord.setPointAmount(monthlyRecord.getPointAmount()+point);
        monthlyRecord.setTotalAmount(monthlyRecord.getTotalAmount()+total);
        return monthlyRecordRepository.save(monthlyRecord);
    }

    public Cafe getCafeById(Long id) {
        return cafeRepository.findById(id).orElse(null);
    }

    public Cafe getCafeByOwner(Long ownerId) {
        return cafeRepository.findByOwnerId(ownerId).orElse(null);
    }

    public List<Cafe> getAllCafe() {
        return cafeRepository.findAll();
    }

    public List<Cafe> getAllActiveCafe() {
        return cafeRepository.findAllByState(CafeState.ACTIVE);
    }

    public List<Cafe> getAllSearchedCafe(String keyword) {
        return cafeRepository.findAllByStateAndNameContaining(CafeState.ACTIVE, keyword);
    }

    /**
     * <h3>추천 카페 리스트 뽑을때 사용함!</h3>
     * 유저의 위치정보와 성향정보를 이용하여 유저에게 적합한 추천 카페 리스트를 반환함
     * <br>getCafeByDirection()과 getCafeByPropensity() 가 함께 적용됨
     *
     * @param user 유저 엔티티
     * @param dir  DirectionDTO 유저의 위치
     * @return 거리 정보와 적합도 정보가 포함되고 적합도가 작은 순대로 정렬된 거리 범위 내의 CafeDisplayDTO 리스트
     */
    public List<CafeDisplayDTO> getCafeListByUser(User user, DirectionDTO dir) {
        return getCafeByPropensity(getCafeByDirection(getAllActiveCafe().stream().map(cafe -> CafeDisplayDTO.builder().cafe(cafe).cafePhoto(cafe.getCafePhotos().stream().findFirst().orElse(null)).build()).collect(Collectors.toList()), dir, distanceRange), user.getUserPropensity(), true);
    }

    /**
     * <h3>검색했을때 성향검색결과에 사용함(제휴카페)!</h3>
     * 검색어로 검색된 카페 중에서 유저의 위치정보와 성향정보를 이용하여 유저에게 적합한 순서대로 카페 리스트를 반환함
     * <br>getAllSearchedCafe()로 키워드가 이름에 포함된 카페리스트를 받아와 사용
     * <br>getCafeByDirection()과 getCafeByPropensity() 가 함께 적용됨
     *
     * @param user    유저 엔티티
     * @param keyword 검색어 키워드
     * @param dir     DirectionDTO 유저의 위치
     * @return 거리 정보와 적합도 정보가 포함되고 적합도가 작은 순대로 정렬된 거리 범위 내의 검색어가 포함된 제휴상태의 CafeDisplayDTO 리스트
     */
    public List<CafeDisplayDTO> searchPartnerCafesByUser(User user, String keyword, DirectionDTO dir) {
        return getCafeByPropensity(getCafeByDirection(getAllSearchedCafe(keyword).stream().map(cafe -> CafeDisplayDTO.builder().cafe(cafe).build()).collect(Collectors.toList()), dir, distanceRange), user.getUserPropensity(), false)
                .stream().filter(dto -> dto.getCafe().isPartnerState()).collect(Collectors.toList());
    }

    /**
     * <h3>검색했을때 성향검색 미적용 결과에 사용함(미제휴카페)!</h3>
     * 검색어로 검색된 카페 중에서 유저의 위치정보와 성향정보를 이용하여 유저에게 적합한 순서대로 카페 리스트를 반환함
     * <br>getAllSearchedCafe()로 키워드가 이름에 포함된 카페리스트를 받아와 사용
     * <br>getCafeByDirection()과 getCafeByPropensity() 가 함께 적용됨
     *
     * @param user    유저 엔티티
     * @param keyword 검색어 키워드
     * @param dir     DirectionDTO 유저의 위치
     * @return 거리 정보와 적합도 정보가 포함되고 거리가 가까운순대로 정렬된 거리 범위 내의 검색어가 포함된 제휴되지 않은 CafeDisplayDTO 리스트
     */
    public List<CafeDisplayDTO> searchNormalCafeListByUser(User user, String keyword, DirectionDTO dir) {
        return getCafeByPropensity(getCafeByDirection(getAllSearchedCafe(keyword).stream().map(cafe -> CafeDisplayDTO.builder().cafe(cafe).build()).collect(Collectors.toList()), dir, distanceRange), user.getUserPropensity(), false)
                .stream().filter(dto -> !dto.getCafe().isPartnerState()).collect(Collectors.toList());
    }

    /**
     * 카페dto 리스트와 유저의위치, 범위를 받아와 카페와 유저의 거리를 계산하고 범위안에 있는 카페만 거리정보를 포함하고 거리순으로 정렬된 리스트로 반환함
     *
     * @param cafeDisplayDtoList 카페를 dto에 담은 dto리스트
     * @param userDir            DirectionDTO 유저의 위치
     * @param range              유저와 카페의 거리의 범위
     * @return 거리 정보가 포함되고 거리가 가까운순대로 정렬된 거리 범위 내의 CafeDisplayDTO 리스트
     */
    public List<CafeDisplayDTO> getCafeByDirection(List<CafeDisplayDTO> cafeDisplayDtoList, DirectionDTO userDir, int range) {
        List<CafeDisplayDTO> newCafeDisplayDtoList = new ArrayList<>();
        cafeDisplayDtoList.forEach(cafeDisplayDTO -> {
            double distance = kakaoLocalApiHelper.calculateDistance(userDir, DirectionDTO.builder().longitude(cafeDisplayDTO.getCafe().getLongitude()).latitude(cafeDisplayDTO.getCafe().getLatitude()).build());
            if (distance <= range) {
                newCafeDisplayDtoList.add(CafeDisplayDTO.builder().cafe(cafeDisplayDTO.getCafe()).distance(distance).build());
            }
        });
        newCafeDisplayDtoList.sort(Comparator.comparing(CafeDisplayDTO::getDistance));
        return newCafeDisplayDtoList;
    }

    /**
     * 카페dto 리스트와 유저의 성향을 받아 카페와 유저의 성향을 비교하여 적합도를 계산 후 카페dto에 적합도를 넣은 후 적합도가 작은순(더 저합함을 의미)대로 정렬하여 반환
     *
     * @param cafeDisplayDTOList 카페를 dto에 담은 dto리스트
     * @param propensity         UserPropensity 유저의 성향
     * @param checkPartnership   카페가 제휴를 맺었는지 확인 여부를 선택함, true일땐 제휴를 맺은(partnership==true) 카페만 반환함
     * @return 적합도 정보가 포함되고 적합도 작은순대로 정렬된 CafeDisplayDTO리스트
     */
    public List<CafeDisplayDTO> getCafeByPropensity(List<CafeDisplayDTO> cafeDisplayDTOList, UserPropensity propensity, boolean checkPartnership) {
        cafeDisplayDTOList.forEach(cafe -> cafe.setFitness(propensityService.calculatePropensity(propensity, cafe.getCafe().getCafePropensity())));
        cafeDisplayDTOList.sort(Comparator.comparing(CafeDisplayDTO::getFitness));
        if (checkPartnership) {
            cafeDisplayDTOList.forEach(cafeDisplayDTO -> {
                Optional<Partnership> partnership = partnershipRepository.findFirstByCafeOrderByEndDateDesc(cafeDisplayDTO.getCafe());
                partnership.ifPresent(value -> value.setViewCount(value.getViewCount() + 1));
            });
            return cafeDisplayDTOList.stream().filter(dto -> dto.getCafe().isPartnerState()).collect(Collectors.toList());
        }
        return cafeDisplayDTOList;
    }
}

