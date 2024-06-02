package com.jongwow.flinkquick.data;

public enum CdcOpCode {


    OP_INSERT("I"),
    OP_UPDATE("U"),
    OP_DELETE("D");

    private final String text;

    CdcOpCode(final String text) {
        this.text = text;
    }

    @Override
    public java.lang.String toString() {
        return this.text;
    }

}
