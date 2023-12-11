package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CafeDisplayDTO {
    private Cafe cafe;
    private double fitness;
    private int distance;
}
