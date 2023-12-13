package com.teamseven.cafeplatform.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectionDTO {
    private double longitude;
    private double latitude;
}
