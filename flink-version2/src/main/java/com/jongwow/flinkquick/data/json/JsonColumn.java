package com.jongwow.flinkquick.data.json;

import java.util.Objects;

public class JsonColumn {
    private final String columnName;
    private final DataType dataType;

    public JsonColumn(String columnName, DataType dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
    }

    public String getColumnName() {
        return columnName;
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (Objects.equals(this.columnName, ((JsonColumn) obj).getColumnName()) && this.dataType == ((JsonColumn) obj).getDataType()) {
            return true;
        }
        return false;
    }
}
