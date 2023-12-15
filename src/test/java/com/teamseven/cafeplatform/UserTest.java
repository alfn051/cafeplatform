package com.teamseven.cafeplatform;

import com.teamseven.cafeplatform.domain.user.common.Gender;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
public class UserTest {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    void login(){
        User user = userService.join(UserJoinDTO.builder()
                .loginId("1")
                .password("1")
                .name("1")
                .email("1")
                .phone("1")
                .birthday(LocalDate.now())
                .gender(Gender.MALE)
                .build());
        User loginUser = userService.login(UserLoginDTO.builder()
                .loginId("1")
                .password("1")
                .build());
        Assertions.assertEquals(user, loginUser);

    }

}
