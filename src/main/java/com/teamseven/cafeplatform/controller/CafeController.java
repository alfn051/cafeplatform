package com.teamseven.cafeplatform.controller;

import ch.qos.logback.core.model.Model;
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

    @GetMapping("/manage/propensity")
    public String cafeManagePropensity(Model model){
        return "/cafe/cafe_manage_propensity";
    }

    @GetMapping("/manage/getmoney")
    public String cafeManageGetMoney(Model model){
        return "/cafe/cafe_manage_getmoney";
    }

    @GetMapping("/manage/propensity/order")
    public String cafeManagePropensityOrder(Model model){
        return "/cafe/cafe_manage_propensity_order";
    }

    @GetMapping("/manage/stamp")
    public String cafeManageStampManage(Model model){
        return "/cafe/cafe_manage_stamp_manage";
    }

    @GetMapping("/add/1")
    public String cafeAddOne(Model model){
        return "/cafe/cafe_add_1";
    }

    @GetMapping("/add/2")
    public String cafeAddTwo(Model model){
        return "/cafe/cafe_add_2";
    }

    @GetMapping("/add/3")
    public String cafeAddThree(Model model){
        return "/cafe/cafe_add_3";
    }


    @GetMapping("/detail")
    public String cafeDetail(Model model){


        return "/cafe/cafeDetail";
    }
}
