package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.stamp.common.TotalStamp;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CafeThemeDTO {
    private long cafeId;
    private String color;
    private StampImage stampImage;
}
