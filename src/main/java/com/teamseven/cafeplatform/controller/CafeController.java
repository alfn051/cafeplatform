package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/cafe")
public class CafeController {
    private final CafeService cafeService;

    @PostMapping("/registration")
    public String registerCafe(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               @ModelAttribute CafeRegistrationDTO dto) {
        if(loginUser == null) return "redirect:/user/signin";
        cafeService.registerCafe(dto.setOwnerId(loginUser.getId()));
        return "redirect:/setting";
    }
}
