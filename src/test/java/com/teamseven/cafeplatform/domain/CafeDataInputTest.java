package com.teamseven.cafeplatform.domain;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.config.KakaoLocalApiHelper;
import com.teamseven.cafeplatform.domain.attendence.repository.AttendanceBoardRepository;
import com.teamseven.cafeplatform.domain.attendence.repository.AttendanceHistoryRepository;
import com.teamseven.cafeplatform.domain.attendence.repository.AttendanceSettingRepository;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.repository.CafeRepository;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.repository.UserRepository;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Log4j2
@RequiredArgsConstructor
public class CafeDataInputTest {
    @Autowired
    UserService userService;
    @Autowired
    CafeService cafeService;
    @Autowired
    KakaoLocalApiHelper kakaoLocalApiHelper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CafeRepository cafeRepository;



//
//
//
//    @Test
//    void 카페_데이터_만들기() {
//
//        /**
//         * 새로운 유저 생성 과 동시에 카페가입
//         */
//
//        // Given
//
//        for (int i = 0; i < 101; i++) {
//            /**
//             *  새로운 유저 생성
//             */
////            User user = userService.join(UserJoinDTO.builder()
////                    .loginId("123" + String.valueOf(i))
////                    .password("1234")
////                    .name("ama" + String.valueOf(i))
////                    .email("123" + String.valueOf(i) + ".com")
////                    .phone("010-1234-1234")
////                    .birthday(LocalDate.now())
////                    .gender(Gender.MALE)
////                    .point(0)
////                    .build());
////
//            User userList = userService.getUserByLoginId("123"+i);
//
////            log.info("user : {}", String.valueOf(user));
////            log.info("cafe: {}",cafe);
//
//
////        cafeList.forEach(System.out::println);
////        cafeList.forEach(str -> System.out.println(str));
//
//            // When
//            DirectionDTO direction;
//            direction = DirectionDTO.builder().longitude(129.07506783124393).latitude(35.17973748292069).build();
//
//            /**
//             * 새로운 카페 등록 ( 유저 1명이 1개의 카페를 등록)
//             */
//            CafeRegistrationDTO cafe = CafeRegistrationDTO.builder()
//                    .name("테스트카페" + "0987" + String.valueOf(i))
//                    .phone("010-1234-1234")
//                    .businessNum("1010101010")
//                    .detailAddress("부산광역시 사상구 가야대로 "+String.valueOf(i))
//                    .baseAddress("주레동 123-"+String.valueOf(i))
//                    .postalCode(47007)
//                    .ownerId(userList.getId())
//                    .introduction("카페설명" + String.valueOf(i))
//                    .build();
//
//            /**
//             *  User 객체 , 색 , 스탬프 이미지 , 위도/경도 입력
//             */
//            // Then
//            cafe.toEntity(userList, "#ffffff", StampImage.A, direction);
//            Cafe cafeResult = cafeService.registerCafe(cafe);
//            log.info("cafeResult: {}",cafeResult);
//
//            assertNotNull(cafeResult);
////            Assertions.assertThat(testCafe).isEqualTo(cafe);
//
//        }
//    }
}
