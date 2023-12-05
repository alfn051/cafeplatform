package com.teamseven.cafeplatform.domain.user.common;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남성"), FEMALE("여성"), NONE("알수없음");

    private final String display;
    Gender(String display){this.display = display;}
}
