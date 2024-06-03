package com.jongwow.flinkquick.data.json;

import java.io.Serializable;
import java.util.Objects;

public class JsonColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String columnName;
    private final JsonDataType jsonDataType;

    public JsonColumn(String columnName, JsonDataType jsonDataType) {
        this.columnName = columnName;
        this.jsonDataType = jsonDataType;
    }

    public String getColumnName() {
        return columnName;
    }

    public JsonDataType getDataType() {
        return jsonDataType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (Objects.equals(this.columnName, ((JsonColumn) o).getColumnName())
                && this.jsonDataType == ((JsonColumn) o).getDataType()) {
            return true;
        }
        return false;
    }
}
