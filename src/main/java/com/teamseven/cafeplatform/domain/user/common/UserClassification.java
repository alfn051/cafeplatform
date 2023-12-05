package com.teamseven.cafeplatform.domain.user.common;

import lombok.Getter;

@Getter
public enum UserClassification {
    NORMAL("일반회원"), OWNER("점주"), ADMIN("관리자");

    private final String display;
    UserClassification(String display){
        this.display = display;
    }
}
