package com.teamseven.cafeplatform.home;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.ReviewDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.cafe.service.ReviewService;
import com.teamseven.cafeplatform.domain.user.common.UserClassification;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final CafeService cafeService;
    private final ReviewService reviewService;

    //학교위치 35.145730, 129.007282

    @GetMapping("/")
    public String Home(@SessionAttribute(name = "loginUser", required = false)User loginUser, HttpServletRequest httpServletRequest , Model model){

        log.info("loginUser : {}", loginUser);

        List<CafeDisplayDTO> cafeDisplayDTOList;
        List<ReviewDisplayDTO> recentReviewList;
        if(loginUser!=null){
            User user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            cafeDisplayDTOList = cafeService.getCafeListByUser(user, DirectionDTO.builder().longitude(129.007282).latitude(35.145730).build());
            recentReviewList = reviewService.getFolloweeRecentReview(user.getId(), 5);
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
            }
        }else {
            cafeDisplayDTOList = cafeService.getAllActiveCafe().stream().map(cafe -> CafeDisplayDTO.builder().cafe(cafe).fitness(null).distance(null).build()).toList();
            recentReviewList = new ArrayList<ReviewDisplayDTO>();
        }
        model.addAttribute("cafeList", cafeDisplayDTOList);
        model.addAttribute("reviewList", recentReviewList);

        return "/home/home";

//    @GetMapping("/list")
//    public String Test(Model model){
//        List<Cafe> cafeList = cafeService.getAllCafe();
//        System.out.println(cafeList);
//        model.addAttribute("cafeList", cafeList);
//
//        return "/home/cafeList";
    }

    @GetMapping("/search")
    public String Search(@RequestParam("search") String search, @SessionAttribute(name = "loginUser", required = false)User loginUser, HttpServletRequest httpServletRequest , Model model){

        log.info("loginUser : {}", loginUser);

        List<CafeDisplayDTO> cafeDisplayDTOListProp = new ArrayList<>();
        List<CafeDisplayDTO> cafeDisplayDTOList;
        if(loginUser!=null){
            User user = userService.getUserById(loginUser.getId());
            model.addAttribute("loginUser", user);
            cafeDisplayDTOListProp = cafeService.searchPartnerCafesByUser(user, search, DirectionDTO.builder().longitude(129.007282).latitude(35.145730).build());
            cafeDisplayDTOList = cafeService.searchNormalCafeListByUser(user, search, DirectionDTO.builder().longitude(129.007282).latitude(35.145730).build());
            if(user.getClassification().equals(UserClassification.OWNER)){
                model.addAttribute("mycafeId", cafeService.getCafeByOwner(user.getId()).getId());
            }
        }else {
            cafeDisplayDTOList = cafeService.getAllSearchedCafe(search).stream().map(cafe -> CafeDisplayDTO.builder().cafe(cafe).fitness(null).distance(null).build()).toList();
        }
        model.addAttribute("cafeListProp", cafeDisplayDTOListProp);
        model.addAttribute("cafeList", cafeDisplayDTOList);

        return "/home/search";

//    @GetMapping("/list")
//    public String Test(Model model){
//        List<Cafe> cafeList = cafeService.getAllCafe();
//        System.out.println(cafeList);
//        model.addAttribute("cafeList", cafeList);
//
//        return "/home/cafeList";
    }
}
