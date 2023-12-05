package com.teamseven.cafeplatform.user.controller;

import com.teamseven.cafeplatform.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.user.entity.User;
import com.teamseven.cafeplatform.user.service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {


    @Autowired
    private final UserService userService;

    @GetMapping("/signin")
    public String SignInPage(){

        return "/common/signin";
    }


    @RequestMapping("/signin")
    public String SignIn(@RequestParam String id, @RequestParam String password){
        HttpSession session  =
            System.out.println(id+password);
            UserLoginDTO login = UserLoginDTO.builder().loginId(id).password(password).build();
            User user = userService.login(login);
            return "/home/index";

        }
    }


