package com.teamseven.cafeplatform.user.controller;

import com.teamseven.cafeplatform.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.user.entity.User;
import com.teamseven.cafeplatform.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signin")
    public String SignInPage() {

        return "user/signin";
    }


    @PostMapping("/signin")
    public String login(@ModelAttribute UserLoginDTO dto, HttpServletRequest httpServletRequest, Model model) {
        User user = userService.login(dto);
        if (user == null) {
            return "user/signin";
        }

        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("loginUser", user);
        session.setMaxInactiveInterval(3600);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}
