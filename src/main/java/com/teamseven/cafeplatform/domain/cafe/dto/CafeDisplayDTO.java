package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CafeDisplayDTO {
    private Cafe cafe;
    private Double fitness;
    private Double distance;
    private String filename;
}
