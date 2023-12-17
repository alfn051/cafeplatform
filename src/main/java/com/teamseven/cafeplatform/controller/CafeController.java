package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.ReviewDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Partnership;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.cafe.service.MenuService;
import com.teamseven.cafeplatform.domain.cafe.service.ReviewService;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.propensity.dto.CafePropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.entity.CafePropensity;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.dto.StampSettingDTO;
import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import com.teamseven.cafeplatform.domain.stamp.entity.StampSetting;
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
    public String cafePage(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
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
    public String cafePageMenu(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
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
    public String cafePageReview(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
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
    public String cafePageStamp(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
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
    public String cafePageOrder(@SessionAttribute(name = "loginUser", required = false) User loginUser, @RequestParam(value = "cafeId") Long cafeId, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
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

    @GetMapping("/cafemanage")
    public String cafeManage(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafemanage";
    }

    @GetMapping("/cafemanagemoney")
    public String cafeManageMoney(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, @RequestParam Integer month) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafemanagemoney";
    }

    @GetMapping("/cafemanagestamp")
    public String cafeManageStamp(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        StampSetting stampSetting = stampService.getRecentSetting(cafe.getId());
        model.addAttribute("stampSetting", stampSetting);

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafemanagestamp";
    }

    @PostMapping("/cafemanagestamp")
    public String cafeManageStampSet(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, @ModelAttribute StampSettingDTO dto) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        stampService.setStampSetting(dto);
        return "redirect:/cafe/cafemanagestamp";
    }

    @GetMapping("/cafemanagepropensity")
    public String cafeManagePropensity(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        CafePropensity propensity = cafe.getCafePropensity();
        model.addAttribute("propensity", propensity);

        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafemanagepropensity";
    }

    @PostMapping("/cafemanagepropensity")
    public String cafeManagePropensitySet(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, @ModelAttribute CafePropensityDTO dto) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        propensityService.setCafePropensity(dto, cafe);
        return "redirect:/cafe/cafemanagepropensity";
    }

    @GetMapping("/cafemanagepartner")
    public String cafeManagePartner(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";
        Partnership partnership = cafeService.checkPartnership(cafe.getId());

        model.addAttribute("partnership", partnership);
        model.addAttribute("cafe", cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("df", df);
        return "cafe/cafemanagepartner";
    }

    @PostMapping("/cafemanagepartner")
    public String cafeManagePartnerAdd(@SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, @ModelAttribute Long cafeId) {

        User user;
        if(loginUser!=null){
            user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            if(!user.getClassification().equals(UserClassification.OWNER)){
                return "redirect:/login";
            }
        }else {
            return "redirect:/login";
        }

        Cafe cafe = cafeService.getCafeByOwner(user.getId());
        if (cafe == null) return "redirect:/";

        cafeService.partnership(cafeId, false);
        return "redirect:/cafe/cafemanagepartner";
    }


//    @GetMapping("/manage/propensity")
//    public String cafeManagePropensity(Model model){
//        return "/cafe/cafe_manage_propensity";
//    }
//
//    @GetMapping("/manage/getmoney")
//    public String cafeManageGetMoney(Model model){
//        return "/cafe/cafe_manage_getmoney";
//    }
//
//    @GetMapping("/manage/propensity/order")
//    public String cafeManagePropensityOrder(Model model){
//        return "/cafe/cafe_manage_propensity_order";
//    }
//
//    @GetMapping("/manage/stamp")
//    public String cafeManageStampManage(Model model){
//        return "/cafe/cafe_manage_stamp_manage";
//    }
//
//    @GetMapping("/add/1")
//    public String cafeAddOne(Model model){
//        return "/cafe/cafe_add_1";
//    }
//
//    @GetMapping("/add/2")
//    public String cafeAddTwo(Model model){
//        return "/cafe/cafe_add_2";
//    }
//
//    @GetMapping("/add/3")
//    public String cafeAddThree(Model model){
//        return "/cafe/cafe_add_3";
//    }
//
//    @GetMapping("/detail")
//    public String cafeDetail(Model model){
//
//
//        return "/cafe/cafeDetail";
//    }
}
