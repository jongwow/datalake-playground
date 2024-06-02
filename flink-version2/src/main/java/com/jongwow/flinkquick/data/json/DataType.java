package com.jongwow.flinkquick.data.json;

public enum DataType {
    BIGINT("bigint"),
    VARCHAR("varchar");

    private final String text;

    DataType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
