package com.jongwow.flinkquick.data.json;

import java.util.ArrayList;
import java.util.List;

public class JsonSchema {
    public List<JsonColumn> columns = new ArrayList<>();
    public String tableName;

    public JsonSchema() {
    }

    public JsonSchema(String tableName) {
        this.tableName = tableName;
    }
}
