package com.teamseven.cafeplatform.append;

import com.teamseven.cafeplatform.domain.cafe.common.Category;
import com.teamseven.cafeplatform.domain.cafe.common.MenuState;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeRegistrationDTO;
import com.teamseven.cafeplatform.domain.cafe.dto.MenuCreateDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.cafe.service.CafeService;
import com.teamseven.cafeplatform.domain.cafe.service.MenuService;
import com.teamseven.cafeplatform.domain.cafe.service.ReviewService;
import com.teamseven.cafeplatform.domain.order.service.OrderService;
import com.teamseven.cafeplatform.domain.propensity.common.Scenery;
import com.teamseven.cafeplatform.domain.propensity.dto.CafePropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.dto.UserPropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.service.PropensityService;
import com.teamseven.cafeplatform.domain.stamp.common.StampExpiration;
import com.teamseven.cafeplatform.domain.stamp.common.TotalStamp;
import com.teamseven.cafeplatform.domain.stamp.dto.StampSettingDTO;
import com.teamseven.cafeplatform.domain.stamp.service.StampService;
import com.teamseven.cafeplatform.domain.user.common.Gender;
import com.teamseven.cafeplatform.domain.user.dto.UserJoinDTO;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.user.service.UserService;
import com.teamseven.cafeplatform.file.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class AppendData {
    @Autowired
    private CafeService cafeService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PropensityService propensityService;
    @Autowired
    private StampService stampService;
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @Test
    void addUser() {
        UserJoinDTO dto = UserJoinDTO.builder()
                .loginId("8888")
                .password("8888")
                .name("손팔팔")
                .email("8888@8888.888")
                .phone("01088888888")
                .birthday(LocalDate.now())
                .gender(Gender.FEMALE)
                .photo(getFile("user8.png", "png"))
                .build();

        User user = userService.join(dto);
        UserPropensityDTO udto = UserPropensityDTO.builder()
                .userId(user.getId())
                .sceneryImportance(4)
                .interiorImportance(7)
                .menuImportance(1)
                .mood(9)
                .sceneryPreferred(Scenery.SEA)
                .menuPreferred(Category.SNACK)
                .build();
        userService.setPropensity(udto);
    }

    @Test
    void addCafe() {
//        CafeRegistrationDTO cdto = CafeRegistrationDTO.builder()
//                .name("이디야커피 동서대IC빌딩정")
//                .phone("05155555555")
//                .businessNum("55555555")
//                .baseAddress("부산 사상구 주례로47")
//                .detailAddress("IC빌딩 2층")
//                .postalCode(47011)
//                .introduction("여러분들도 다 아는 그 카페입니다")
//                .ownerId(18)
//                .photo(getFile("cafe5.png", "png"))
//                .build();
//        Cafe cafe = cafeService.registerCafe(cdto);
//        CafePropensityDTO pdto = CafePropensityDTO.builder()
//                .cafeId(3)
//                .sceneryImportance(10)
//                .interiorImportance(1)
//                .menuImportance(10)
//                .mood(1)
//                .sceneryType(Scenery.CITY)
//                .menuFocused(Category.COFFEE)
//                .build();
//        cafeService.setPropensity(pdto);
//        Menu menu1 = addMenu(cafe.getId(), "아메리카노", 1000, "고급원두 아메리카노입니다", Category.COFFEE, MenuState.ACTIVE, "1");
//        Menu menu2 = addMenu(cafe.getId(), "모카", 1000, "코코아향이나는 모카입니다.", Category.COFFEE, MenuState.ACTIVE, "2");
//        addMenu(cafe.getId(), "오렌지주스", 1000, "리얼오렌지과즙", Category.BEVERAGE, MenuState.ACTIVE, "3");
//        addMenu(cafe.getId(), "녹차", 1000, "보성녹차", Category.TEA, MenuState.ACTIVE, "4");
//        addMenu(cafe.getId(), "에그타르트", 1000, "계란을 두개나 넣은 에그타르트", Category.BREAD, MenuState.ACTIVE, "5");
//        addMenu(cafe.getId(), "샌드위치", 1000, "BLT 샌드위치", Category.MEAL, MenuState.ACTIVE, "6");
//        addMenu(cafe.getId(), "초콜릿스낵", 1000, "허쉬초콜릿 아닙니다.", Category.SNACK, MenuState.ACTIVE, "7");
//        StampSettingDTO sdto = StampSettingDTO.builder()
//                .cafeId(cafe.getId())
//                .totalStamp(TotalStamp.TEN)
//                .expiration(StampExpiration.MONTH)
//                .criterionAmount(2000)
//                .eventFirst(false)
//                .eventBirthday(false)
//                .giftFirstId(menu1.getId())
//                .giftSecondId(menu2.getId())
//                .build();
//        stampService.setStampSetting(sdto);
    }

    public Menu addMenu(long cafeId, String name, long price, String information, Category category, MenuState state, String pn) {
        return cafeService.addMenu(MenuCreateDTO.builder()
                .cafeId(cafeId)
                .name(name)
                .price(price)
                .information(information)
                .category(category)
                .state(state)
                .photo(getFile(("menu"+pn+".png"), "png"))
                .build());
    }


    public MultipartFile getFile(String fname, String type) {
        Resource resource = new FileSystemResource(System.getProperty("user.dir") + "/src/main/resources/static/images/dummy/" + fname);
        String name = "file";
        String originalFileName = resource.getFilename();
        String contentType = "image/" + type; // 파일의 실제 타입에 따라 변경해야 합니다.

        MultipartFile multipartFile = null;
        try {
            InputStream is = resource.getInputStream();
            multipartFile = new MockMultipartFile(name, originalFileName, contentType, is.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();

        }
        return multipartFile;
    }
}