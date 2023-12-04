package com.teamseven.cafeplatform.stamp.common;

import lombok.Getter;

@Getter
public enum BoardState {
    ACTIVE("활성화"), EXPIRED("만료"), ACHIEVED("달성");
    private final String display;

    BoardState(String display) {
        this.display = display;
    }
}
