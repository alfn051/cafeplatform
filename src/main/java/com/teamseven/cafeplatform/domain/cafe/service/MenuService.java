package com.teamseven.cafeplatform.domain.cafe.service;

import com.teamseven.cafeplatform.domain.cafe.dto.MenuCreateDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.repository.MenuRepository;
import com.teamseven.cafeplatform.file.common.Classify;
import com.teamseven.cafeplatform.file.entity.Photo;
import com.teamseven.cafeplatform.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;
    private final CafeRepository cafeRepository;
    private final FileService fileService;

    @Transactional
    public Menu createMenu(MenuCreateDTO dto, Cafe cafe) {
        Menu menu = menuRepository.save(dto.toEntity(cafe));
        try {
            menu.setPhoto(fileService.storePhoto(dto.getPhoto(), Classify.MENU).orElseThrow());
        } catch (IOException e) {
            log.error("메뉴등록시 사진저장 실패 : " + e.getMessage());
        }
        return menuRepository.save(menu);
    }
}
