package com.teamseven.cafeplatform.domain.stamp.common;

import lombok.Getter;

@Getter
public enum StampExpiration {
    MONTH("한달",1), QUARTER("분기", 3), HALF("반기", 6), YEAR("1년", 12);

    private final String display;
    private final int month;

    StampExpiration(String display, int month) {
        this.display = display;
        this.month = month;
    }
}
