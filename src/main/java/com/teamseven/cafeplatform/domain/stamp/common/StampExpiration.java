package com.teamseven.cafeplatform.domain.stamp.common;

import lombok.Getter;

@Getter
public enum StampExpiration {
    MONTH("한달"), QUARTER("분기"), HALF("반기"), YEAR("1년");

    private final String display;

    StampExpiration(String display) {
        this.display = display;
    }
}
