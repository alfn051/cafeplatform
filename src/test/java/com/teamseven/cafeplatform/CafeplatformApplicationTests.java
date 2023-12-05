package com.teamseven.cafeplatform;

import com.teamseven.cafeplatform.user.dto.UserLoginDTO;
import com.teamseven.cafeplatform.user.entity.User;
import com.teamseven.cafeplatform.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CafeplatformApplicationTests {


    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
    }


    @Test
    public void 로그인(){

        UserLoginDTO login = UserLoginDTO.builder().loginId("1234").password("1234").build();

        User user = userService.login(login);
//        System.out.println("!!"+user.toString());

        assertThat(user.getLoginId()).isEqualTo("1234");
        assertThat(user.getPassword()).isEqualTo("1234");


    }

}
