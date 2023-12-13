package com.teamseven.cafeplatform.domain.stamp.common;

public enum StampType {
    A(""), B(""), C(""), D("");

    private final String directory;

    StampType(String directory) {
        this.directory = directory;
    }
}