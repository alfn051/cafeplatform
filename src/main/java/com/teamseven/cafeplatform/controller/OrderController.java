package com.teamseven.cafeplatform.controller;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.cafe.service.MenuService;
import com.teamseven.cafeplatform.domain.cafe.service.ReviewService;
import com.teamseven.cafeplatform.domain.order.common.AddCartDTO;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.entity.StampGift;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.common.UserClassification;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("order")
@Slf4j
public class OrderController {
    private final CafeService cafeService;
    private final UserService userService;
    private final PropensityService propensityService;
    private final StampService stampService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final ReviewService reviewService;

    @GetMapping
    public String createOrder(@RequestParam("cafeId") Long cafeId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        orderService.cancelOrder(user.getId(), cafeId);
        Order order = orderService.createPreparingOrder(user, cafe);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("order", order);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "redirect:/order/" + cafeId;
    }

    @GetMapping("/{cafeId}")
    public String orderPage(@PathVariable("cafeId") Long cafeId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, RedirectAttributes redirectAttributes) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            return "redirect:";
        }

        Order order = orderService.getPreparingOrderBtUserAndCafe(user.getId(), cafeId);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("order", order);
        model.addAttribute("cafe", cafe);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "order/orderpage";
    }

    @PostMapping("/{cafeId}/addcart")
    public String addCart(@PathVariable("cafeId") Long cafeId,
                          @SessionAttribute(name = "loginUser", required = false) User loginUser,
                          Model model, RedirectAttributes redirectAttributes,
                          @ModelAttribute AddCartDTO dto,
                          HttpServletRequest httpServletRequest
    ) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            log.error("cafeId가 넘어오지 않았음: " +cafeId);
            return "redirect:"+ httpServletRequest.getHeader("Referer");
        }

        orderService.addCart(dto);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("cafe", cafe);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "redirect:"+ httpServletRequest.getHeader("Referer");
    }

    @GetMapping("/{cafeId}/cart")
    public String orderPageCart(@PathVariable("cafeId") Long cafeId, @SessionAttribute(name = "loginUser", required = false) User loginUser, Model model, RedirectAttributes redirectAttributes) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            return "redirect:";
        }

        Order order = orderService.getPreparingOrderBtUserAndCafe(user.getId(), cafeId);
        List<StampGift> gifts = stampService.getAllStampGiftByUserAndCafe(user.getId(), cafeId);
        int stampAvailable = (int)(order.getAmount() / stampService.getRecentSetting(order.getCafe().getId()).getCriterionAmount());
        model.addAttribute("stampAvailable", stampAvailable);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("order", order);
        model.addAttribute("cafe", cafe);
        model.addAttribute("gifts", gifts);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "order/orderpagecart";
    }

    @PostMapping("/{cafeId}/usegift")
    public String useGift(@PathVariable("cafeId") Long cafeId,
                          @SessionAttribute(name = "loginUser", required = false) User loginUser,
                          Model model, RedirectAttributes redirectAttributes,
                          @ModelAttribute AddCartDTO dto,
                          @ModelAttribute Long orderId,
                          @ModelAttribute Long stampGiftId,
                          HttpServletRequest httpServletRequest
    ) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            return "redirect:"+ httpServletRequest.getHeader("Referer");
        }

        orderService.useGift(orderId, stampGiftId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("cafe", cafe);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "redirect:"+ httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/{cafeId}/usepoint")
    public String usePoint(@PathVariable("cafeId") Long cafeId,
                          @SessionAttribute(name = "loginUser", required = false) User loginUser,
                          Model model, RedirectAttributes redirectAttributes,
                          @ModelAttribute AddCartDTO dto,
                          @ModelAttribute Long orderId,
                          @ModelAttribute Long point,
                          HttpServletRequest httpServletRequest
    ) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            return "redirect:"+ httpServletRequest.getHeader("Referer");
        }

        orderService.usePoint(orderId, point);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("cafe", cafe);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        return "redirect:"+ httpServletRequest.getHeader("Referer");
    }

    @PostMapping("/{cafeId}/confirm")
    public String confirmOrder(@PathVariable("cafeId") Long cafeId,
                          @SessionAttribute(name = "loginUser", required = false) User loginUser,
                          Model model, RedirectAttributes redirectAttributes,
                          @ModelAttribute("orderId") Long orderId,
                          HttpServletRequest httpServletRequest
    ) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            return "redirect:"+ httpServletRequest.getHeader("Referer");
        }

        orderService.confirmOrder(orderId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("cafe", cafe);
        model.addAttribute("loginUser", user);
        model.addAttribute("df", df);
        redirectAttributes.addAttribute("cafeId", cafe.getId());
        redirectAttributes.addAttribute("orderId", orderId);
        return "redirect:/";
    }

    @GetMapping("/confirmed")
    public String confirmOrderPage(@ModelAttribute("cafeId") Long cafeId,
                               @SessionAttribute(name = "loginUser", required = false) User loginUser,
                               Model model, RedirectAttributes redirectAttributes,
                               @ModelAttribute("orderId") Long orderId,
                               HttpServletRequest httpServletRequest
    ) {
        if (loginUser == null) {
            return "redirect:/user/login";
        }
        User user = userService.getUserById(loginUser.getId());
        Cafe cafe = cafeService.getCafeById(cafeId);
        if(cafe == null){
            redirectAttributes.addAttribute("cafeId", cafeId);
            return "redirect:"+ httpServletRequest.getHeader("Referer");
        }
        Order order = orderService.getPreparingOrderBtUserAndCafe(user.getId(), cafeId);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss zzz");
        model.addAttribute("cafe", cafe);
        model.addAttribute("loginUser", user);
        model.addAttribute("order", order);
        model.addAttribute("df", df);
        return "/order/orderpageconfirm";
    }
}
