package com.jongwow.flinkquick.data.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JsonSchema implements Serializable {
    private static final long serialVersionUID = 1L;

    public List<JsonColumn> columns = new ArrayList<>();
    public String tableName;

    public JsonSchema() {
    }

    public JsonSchema(String tableName) {
        this.tableName = tableName;
    }
}
