package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.file.entity.CafePhoto;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class CafeDisplayDTO {
    private Cafe cafe;
    private Double fitness;
    private Double distance;
    private CafePhoto cafePhoto;
}
