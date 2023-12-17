package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.common.Evaluate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewEvaluateDTO {
    private Long userId;
    private Long reviewId;
    private Evaluate evaluate;
}
