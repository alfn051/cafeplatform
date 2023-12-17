package com.teamseven.cafeplatform.domain.stamp.common;

import lombok.Getter;

@Getter
public enum StampType {
    A(""), B(""), C(""), D("");

    private final String directory;

    StampType(String directory) {
        this.directory = directory;
    }
}
