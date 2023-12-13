package com.teamseven.cafeplatform.domain.cafe.common;

import lombok.Getter;

@Getter
public enum StampImage {
    A("stamp_image_01"), B("stamp_image_02"), C("stamp_image_03"), D("stamp_image_04");

    private final String filename;

    StampImage(String filename) {
        this.filename=filename;
    }
}
