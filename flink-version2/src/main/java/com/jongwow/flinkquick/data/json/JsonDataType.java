package com.jongwow.flinkquick.data.json;

public enum JsonDataType {
    BIGINT("bigint"),
    VARCHAR("varchar");

    private final String text;

    JsonDataType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
