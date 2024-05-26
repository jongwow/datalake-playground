package com.jongwow.flinkapp.config;

public class SourceColumn {
    public String name;
    public String type;
    public String expression;

    public SourceColumn(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public SourceColumn(String name, String type, String expression) {
        this.name = name;
        this.type = type;
        this.expression = expression;
    }
}
