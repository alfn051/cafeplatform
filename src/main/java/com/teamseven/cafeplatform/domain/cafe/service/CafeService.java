package com.teamseven.cafeplatform.domain.cafe.service;

import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Partnership;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.PartnershipRepository;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import com.teamseven.cafeplatform.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CafeService {
    private final CafeRepository cafeRepository;
    private final UserService userService;
    private final FileService fileService;
    private final PartnershipRepository partnershipRepository;

    @Transactional
    public Cafe registerCafe(CafeRegistrationDTO dto) {
        User owner = userService.getUserById(dto.getOwnerId());
        if (owner == null) return null;
        Cafe cafe = cafeRepository.save(dto.toEntity(owner));
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
}

