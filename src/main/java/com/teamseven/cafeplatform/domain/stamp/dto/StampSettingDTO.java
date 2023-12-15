package com.teamseven.cafeplatform.domain.stamp.dto;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.stamp.common.StampExpiration;
import com.teamseven.cafeplatform.domain.stamp.common.TotalStamp;
import com.teamseven.cafeplatform.domain.stamp.entity.StampSetting;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StampSettingDTO {
    private Long cafeId;
    private TotalStamp totalStamp;
    private StampExpiration expiration;
    private long criterionAmount;
    private boolean eventFirst;
    private boolean eventBirthday;
    private Long giftFirstId;
    private Long giftSecondId;

    public StampSetting toEntity(Cafe cafe, Menu gift1, Menu gift2) {
        return StampSetting.builder()
                .totalStamp(totalStamp)
                .expiration(expiration)
                .criterionAmount(criterionAmount)
                .cafe(cafe)
                .giftFirst(gift1)
                .giftSecond(gift2)
                .build();
    }
}
