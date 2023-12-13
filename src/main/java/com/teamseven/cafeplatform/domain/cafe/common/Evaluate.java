package com.teamseven.cafeplatform.domain.cafe.common;

import lombok.Getter;

@Getter
public enum Evaluate {
    LIKE("유용함", 1), UNLIKE("유용하지않음", -1);

    private final String display;
    private final int value;
    Evaluate(String display, int value) {
        this.display = display;
        this.value = value;
    }
}
