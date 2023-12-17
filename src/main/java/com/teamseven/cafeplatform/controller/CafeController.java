package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.ReviewDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.cafe.service.MenuService;
import com.teamseven.cafeplatform.domain.cafe.service.ReviewService;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.common.UserClassification;
import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/cafe")
public class CafeController {

    private final CafeService cafeService;
    private final UserService userService;
    private final PropensityService propensityService;
    private final StampService stampService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    @PostMapping("/registration")
    public String registerCafe(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                               @ModelAttribute CafeRegistrationDTO dto) {
        if(loginUser == null) return "redirect:/user/signin";
        cafeService.registerCafe(dto.setOwnerId(loginUser.getId()));
        return "redirect:/setting";
    }

    @GetMapping("/cafepage")
    public String myPageCafe(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
            }
        }

        Cafe cafe = cafeService.getCafeById(cafeId);
        if (cafe == null) return "redirect:/";

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafepage";
    }

    @GetMapping("/cafepagemenu")
    public String myPageCafeMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
            }
        }

        Cafe cafe = cafeService.getCafeById(cafeId);
        if (cafe == null) return "redirect:/";

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafepagemenu";
    }

    @GetMapping("/cafepagereview")
    public String myPageCafeReview(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
            }
        }

        Cafe cafe = cafeService.getCafeById(cafeId);
        if (cafe == null) return "redirect:/";

        List<ReviewDisplayDTO> reviews = reviewService.getAllReviewByCafe(cafeId);
        model.addAttribute("reviews", reviews);

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafepagereview";
    }

    @GetMapping("/cafepagestamp")
    public String myPageCafeStamp(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeById(cafeId);
        if (cafe == null) return "redirect:/";

        model.addAttribute("cafe", cafe);

        StampBoard stampBoard = stampService.getUsersCafeStampBoard(user.getId(), cafeId);
        model.addAttribute("board", stampBoard);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafepagestamp";
    }

    @GetMapping("/cafepageorder")
    public String myPageCafeOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()));
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeById(cafeId);
        if (cafe == null) return "redirect:/";

        model.addAttribute("cafe", cafe);

        List<Order> orders = orderService.getAllOrderByUserAndCafe(user.getId(), cafeId);
        model.addAttribute("orders", orders);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafepageorder";
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
