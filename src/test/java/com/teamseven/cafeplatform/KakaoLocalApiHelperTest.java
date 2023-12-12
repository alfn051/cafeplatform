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

    @Test
    void 거리계산() {
        DirectionDTO dir1 = DirectionDTO.builder().latitude(35.145169).longitude(129.008726).build();
        DirectionDTO dir2 = DirectionDTO.builder().latitude(35.151049).longitude(129.011675).build();
        double cal = kakaoLocalApiHelper.calculateDistance(dir1, dir2);
        System.out.println("거리계산 결과 : "+ cal);
    }
}
