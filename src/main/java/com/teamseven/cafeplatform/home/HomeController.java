package com.teamseven.cafeplatform.home;

import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String Home(@SessionAttribute(name = "loginUser", required = false)User loginUser, HttpServletRequest httpServletRequest , Model model){

        System.out.println("!!!"+loginUser);
        if(loginUser!=null){
            model.addAttribute("loginUser", loginUser);
        }

        return "/home/index";
    }
}
