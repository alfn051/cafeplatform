package com.teamseven.cafeplatform.domain.cafe.common;

import lombok.Getter;

@Getter
public enum Category {
    COFFEE("커피"), TEA("차"), BEVERAGE("음료"), BREAD("빵"), MEAL("식사"), SNACK("간식");
    private final String display;
    Category(String display) {
        this.display = display;
    }
}
