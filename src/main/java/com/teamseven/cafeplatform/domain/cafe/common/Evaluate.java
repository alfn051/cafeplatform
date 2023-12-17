package com.teamseven.cafeplatform.domain.cafe.common;

import lombok.Getter;

@Getter
public enum Evaluate {
    LIKE("유용함", 1), UNLIKE("유용하지않음", -1);

    private final String display;
    private final int number;
    Evaluate(String display, int number) {
        this.display = display;
        this.number = number;
    }
}
