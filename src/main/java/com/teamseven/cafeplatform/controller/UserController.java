package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;


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

    @GetMapping("/info")
    public String myInfo(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/myinfo";
    }

    /**
     * 다른 유저를 팔로우 할 때 호출
     * @param followeeId 팔로우할 유저의 id
     * @param loginUser 현재 로그인 하고있는 유저
     * @return 로그인 안돼있으면 로그인페이지로, 팔로우 성공하면 이전페이지로 리다이렉트
     */
    @GetMapping("/follow")
    public String follow(@RequestParam("followee") Long followeeId,
                         @SessionAttribute(name = "loginUser", required = false) User loginUser,
                         HttpServletRequest httpServletRequest,
                         Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        userService.follow(loginUser.getId(), followeeId);
        return "redirect:"+httpServletRequest.getHeader("Referer"); //이전페이지로 리다이렉트
    }

    @GetMapping("/unfollow")
    public String unFollow(@RequestParam("follow") Long followId,
                           @SessionAttribute(name = "loginUser", required = false) User loginUser,
                           HttpServletRequest httpServletRequest,
                           Model model){
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        userService.unFollow(followId);
        return "redirect:"+httpServletRequest.getHeader("Referer"); //이전페이지로 리다이렉트
    }

}
