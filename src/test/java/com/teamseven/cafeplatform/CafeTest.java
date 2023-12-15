package com.teamseven.cafeplatform;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.config.KakaoLocalApiHelper;
import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.user.common.Gender;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
public class CafeTest {
    @Autowired
    UserService userService;
    @Autowired
    CafeService cafeService;
    @Autowired
    KakaoLocalApiHelper kakaoLocalApiHelper;

    @Test
    @Transactional
    void 카페등록(){
        User user = userService.join(UserJoinDTO.builder()
                .loginId("1")
                .password("1")
                .name("1")
                .email("1@1.com")
                .phone("1")
                .birthday(LocalDate.now())
                .gender(Gender.MALE)
                .build());
        CafeRegistrationDTO dto = CafeRegistrationDTO.builder()
                .name("테스트카페")
                .phone("01000000000")
                .businessNum("111100001111")
                .baseAddress("가야대로298")
                .detailAddress("산학협력관 5203")
                .postalCode(49000)
                .introduction("이카페는 매우 좋습니다!")
                .ownerId(user.getId())
                .build();
        DirectionDTO direction;
        try {
            direction = kakaoLocalApiHelper.getDirectionByKeyword(dto.getBaseAddress());
        } catch (Exception e) {
            direction = DirectionDTO.builder().longitude(129.07506783124393).latitude(35.17973748292069).build();
        }
        Cafe cafe = dto.toEntity(user, "#8a4500", StampImage.A, direction, CafeState.PREPARING);
        Cafe testCafe = cafeService.registerCafe(dto);

        Assertions.assertThat(testCafe).isEqualTo(cafe);
    }
}
