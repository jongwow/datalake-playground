package com.jongwow.flinkquick.data.json;

public class JsonConverter {
    public static DataType convertDataType(String dataType){
        if (dataType.equalsIgnoreCase("number")) {
            return DataType.BIGINT;
        }
        if (dataType.equalsIgnoreCase("string")) {
            return DataType.VARCHAR;
        }
        throw new IllegalArgumentException("Invalid data type");
    }
    public static JsonColumn convertJsonColumn(String raw){
        String[] split = raw.split(" ");
        if (split.length != 2) {
            throw new IllegalArgumentException("Invalid json column");
        }
        String columnName = split[0];
        String dataType = split[1];
        return new JsonColumn(columnName, convertDataType(dataType));
    }
}
