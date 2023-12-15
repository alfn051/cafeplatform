package com.teamseven.cafeplatform.home;

import com.teamseven.cafeplatform.domain.cafe.dto.CafeDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    private final CafeService cafeService;

    @GetMapping("/")
    public String Home(@SessionAttribute(name = "loginUser", required = false)User loginUser, HttpServletRequest httpServletRequest , Model model){

        List<Cafe> cafeList = cafeService.getAllCafe();
        System.out.println(cafeList);
        model.addAttribute("cafeList", cafeList);

        log.info("loginUser : {}", loginUser);

        if(loginUser!=null){
            model.addAttribute("loginUser", loginUser);
        }

        return "/home/index";

//    @GetMapping("/list")
//    public String Test(Model model){
//        List<Cafe> cafeList = cafeService.getAllCafe();
//        System.out.println(cafeList);
//        model.addAttribute("cafeList", cafeList);
//
//        return "/home/cafeList";
    }
}
