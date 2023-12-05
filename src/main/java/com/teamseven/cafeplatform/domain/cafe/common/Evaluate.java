package com.teamseven.cafeplatform.domain.cafe.common;

import lombok.Getter;

@Getter
public enum Evaluate {
    LIKE("유용함"), UNLIKE("유용하지않음");

    private final String display;
    Evaluate(String display) {
        this.display = display;
    }
}
