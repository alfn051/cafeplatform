package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.file.entity.ReviewPhoto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewDisplayDTO {
    private Review review;
    private ReviewPhoto reviewPhoto;
}
