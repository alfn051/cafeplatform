package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final CafeService cafeService;

    private final CafeRepository cafeRepository;

    @GetMapping("/signin")
    public String signInPage() {
        return "user/signin";
    }


    @PostMapping("/signin")
    public String login(@ModelAttribute UserLoginDTO dto, HttpServletRequest httpServletRequest, Model model) {
        System.out.println("signin:"+dto);
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
        return "user/mypage";
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

    /**
     * 페이지
     *
     */

    @GetMapping("/mypage/1")
    public String mypageOne(Model model, @SessionAttribute(name = "loginUser" , required = false) User loginUser){

        model.addAttribute("loginUser", loginUser);

        System.out.println(loginUser);
        User userInfo = userService.getUserByLoginId(loginUser.getLoginId());
        model.addAttribute("userInfo", loginUser);
//        System.out.println(userInfo);


        return "user/mypage_one";
    }

    @GetMapping("/mypage/2")
    public String mypageTwo(Model model){
        return "user/mypage_two";
    }

    @GetMapping("/mypage/3")
    public String mypageThree(Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){

        List<Cafe> cafeList = cafeRepository.findByOwnerId(loginUser.getId());
        System.out.println("!!!"+cafeList);


        model.addAttribute("loginUser", loginUser);

        return "user/mypage_three";
    }

    @GetMapping("/mypage/4")
    public String mypageFour(Model model)
    {
        return "user/mypage_four";
    }

    @GetMapping("/mypage/5")
    public String mypageFive(Model model){
        return "user/mypage_stamp_get";
    }

    /**
     * 마이페이지
     * read / create only
     */


//    @PostMapping("/mypage/1")
//    public String mypageOne(@RequestParam HashMap<String, Object> data){
//        System.out.println("!!!"+ data);
//
//        return "redirect:/";
//
//    }



}
