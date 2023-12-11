package com.teamseven.cafeplatform.domain.cafe.service;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.config.KakaoLocalApiHelper;
import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeThemeDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Partnership;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.PartnershipRepository;
import com.teamseven.cafeplatform.domain.propensity.dto.CafePropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import com.teamseven.cafeplatform.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeService {
    private final CafeRepository cafeRepository;
    private final PartnershipRepository partnershipRepository;
    private final UserService userService;
    private final FileService fileService;
    private final PropensityService propensityService;
    private final KakaoLocalApiHelper kakaoLocalApiHelper;

    @Value("${default_color}")
    private String defaultColor;
    private final StampImage defaultStampImage = StampImage.A;

    @Transactional
    public Cafe registerCafe(CafeRegistrationDTO dto) {
        User owner = userService.getUserById(dto.getOwnerId());
        if (owner == null) return null;

        DirectionDTO direction;
        try {
            direction = kakaoLocalApiHelper.getDirectionByKeyword(dto.getBaseAddress());
        } catch (Exception e) {
            log.error("주소 api에서 위도 경도 가져오는데 오류가 발생함: "+ e.getMessage());
            direction = DirectionDTO.builder().longitude(129.07506783124393).latitude(35.17973748292069).build();
        }

        Cafe cafe = cafeRepository.save(dto.toEntity(owner, defaultColor, defaultStampImage, direction));
        //제휴 첫등록 2개월 무료 등록
        partnershipRepository.save(Partnership.builder()
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusMonths(2))
                .freeTrial(true)
                .build());
        try { //사진 파일 저장
            fileService.storeCafePhotos(dto.getPhotoList(), cafe);
        } catch (IOException e) {
            log.error("카페 등록 사진 파일 저장 실패");
        }
        return cafe;
    }

    @Transactional
    public Cafe setTheme(CafeThemeDTO dto){
        Optional<Cafe> optionalCafe = cafeRepository.findById(dto.getCafeId());
        if(optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        cafe.setColor(dto.getColor());
        cafe.setStampImage(dto.getStampImage());
        return cafeRepository.save(cafe);
    }

    @Transactional
    public Cafe setPropensity(CafePropensityDTO dto){
        Optional<Cafe> optionalCafe = cafeRepository.findById(dto.getCafeId());
        if(optionalCafe.isEmpty()) return null;
        Cafe cafe = optionalCafe.get();
        propensityService.setCafePropensity(dto, cafe);
        return cafe;
    }

    public Cafe getCafeById(Long id){
        return cafeRepository.findById(id).orElse(null);
    }

    public List<Cafe> getAllCafe(){
        return cafeRepository.findAll();
    }

    public List<Cafe> getAllActiveCafe(){
        return cafeRepository.findAllByState(CafeState.ACTIVE);
    }
}

