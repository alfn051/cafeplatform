package com.teamseven.cafeplatform;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.config.KakaoLocalApiHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class KakaoLocalApiHelperTest {
    @Autowired
    private KakaoLocalApiHelper kakaoLocalApiHelper;

    @Test
    @Transactional
    void testDirection() throws Exception {
        DirectionDTO dto = kakaoLocalApiHelper.getDirectionByKeyword("부산광역시청");
        System.out.println("주소api테스트 : "+ dto.toString());
    }

    @Test
    @Transactional
    void testRegion() throws Exception {
        String region = kakaoLocalApiHelper.getRegionByCoords(127.10459896729914, 37.40269721785548);
        System.out.println("주소api테스트 : "+ region);
    }
}
