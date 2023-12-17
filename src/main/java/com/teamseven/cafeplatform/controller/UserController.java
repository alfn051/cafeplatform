package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.propensity.dto.UserPropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.common.UserClassification;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
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

    private final StampService stampService;

    private final OrderService orderService;
    private final PropensityService propensityService;

    @GetMapping("/login")
    public String signInPage() {
        return "user/userLoginForm";
    }


    @PostMapping("/login")
    public String login(@ModelAttribute UserLoginDTO dto, HttpServletRequest httpServletRequest, Model model) {
        User user = userService.login(dto);
        if (user == null) {
            return "user/userLoginForm";
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

    @GetMapping("/mypage")
    public String myPage(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(loginUser.getId());
        if(user.getClassification().equals(UserClassification.OWNER)){
            model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/mypage";
    }

    @GetMapping("/mypage/cafe")
    public String myPageCafe(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(loginUser.getId());

        List<CafeMember> memberCafeList = user.getCafeMembers();
        model.addAttribute("memberCafeList", memberCafeList);

        if(user.getClassification().equals(UserClassification.OWNER)){
            model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/mypagecafe";
    }

    @GetMapping("/mypage/stamp")
    public String myPageStamp(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(loginUser.getId());

        List<StampBoard> stampBoards = stampService.getAllUsersStampBoard(user.getId());
        model.addAttribute("stampBoards", stampBoards);

        if(user.getClassification().equals(UserClassification.OWNER)){
            model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/mypagestamp";
    }

    @GetMapping("/mypage/order")
    public String myPageOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(loginUser.getId());

        List<Order> orders = orderService.getAllOrderByUser(user.getId());
        model.addAttribute("orders", orders);

        if(user.getClassification().equals(UserClassification.OWNER)){
            model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/mypageorder";
    }

    @GetMapping("/mypage/propensity")
    public String myPagePropensity(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(loginUser.getId());

        model.addAttribute("propensity", user.getUserPropensity());

        if(user.getClassification().equals(UserClassification.OWNER)){
            model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "user/mypagepropensity";
    }

    @PostMapping("/mypage/propensity")
    public String addUserPropensity(@SessionAttribute(name = "loginUser", required = false) User loginUser, UserPropensityDTO dto, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }

        User user = userService.getUserById(loginUser.getId());

        model.addAttribute("propensity", user.getUserPropensity());

        propensityService.setUserPropensity(dto, user);

        if(user.getClassification().equals(UserClassification.OWNER)){
            model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "redirect:/user/mypage/propensity";
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







//    /**
//     * 페이지
//     *
//     */

//    @GetMapping("/mypage/1")
//    public String mypageOne(Model model, @SessionAttribute(name = "loginUser" , required = false) User loginUser){
//
//        model.addAttribute("loginUser", loginUser);
//
//        System.out.println(loginUser);
//        User userInfo = userService.getUserByLoginId(loginUser.getLoginId());
//        model.addAttribute("userInfo", loginUser);
////        System.out.println(userInfo);
//
//
//        return "user/mypage_one";
//    }
//
//    @GetMapping("/mypage/2")
//    public String mypageTwo(Model model){
//        return "user/mypage_two";
//    }
//
//    @GetMapping("/mypage/3")
//    public String mypageThree(Model model, @SessionAttribute(name = "loginUser", required = false) User loginUser){
//        //서비스에서 필오한 기능 찾아서 호출하기
//        //List<Cafe> cafeList = cafeRepository.findByOwnerId(loginUser.getId());
//        //System.out.println("!!!"+cafeList);
//
//
//        model.addAttribute("loginUser", loginUser);
//
//        return "user/mypage_three";
//    }
//
//    @GetMapping("/mypage/4")
//    public String mypageFour(Model model)
//    {
//        return "user/mypage_four";
//    }
//
//    @GetMapping("/mypage/5")
//    public String mypageFive(Model model){
//        return "user/mypage_stamp_get";
//    }
//
//
//    /**
//     * 마이페이지
//     * read / create only
//     */


//    @PostMapping("/mypage/1")
//    public String mypageOne(@RequestParam HashMap<String, Object> data){
//        System.out.println("!!!"+ data);
//
//        return "redirect:/";
//
//    }



}
