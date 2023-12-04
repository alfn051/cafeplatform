package com.teamseven.cafeplatform.propensity.common;

import lombok.Getter;

@Getter
public enum Scenery {
    MOUNTAIN("산"), SEA("바다"), CITY("도시");

    private final String display;

    Scenery(String display) {
        this.display = display;
    }
}
