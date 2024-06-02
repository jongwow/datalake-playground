package com.jongwow.flinkapp.sql;

import java.util.*;

public class SourceKafkaTableDefinition {
    private String tableName;
    private List<String> columns = new ArrayList<>();
    private Map<String, String> kafkaConfig = new TreeMap<>();
    private String topic;
    private String valueFormat;
    private String scanStartupMode;


    public SourceKafkaTableDefinition(String tableName) {
        this.tableName = tableName;
    }

    public void addColumn(String columnName, String dataType, String metadata) {
        columns.add("`" + columnName + "` " + dataType + (metadata != null ? " METADATA " + metadata : ""));
    }

    public void setKafkaConfig(String key, String value) {
        kafkaConfig.put(key, value);
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setValueFormat(String valueFormat) {
        this.valueFormat = valueFormat;
    }
    public void setScanStartupMode(String scanMode){
        this.scanStartupMode = scanMode;
    }

    public String build() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tableName).append(" ( ");
        sb.append(String.join(", ", columns));
        sb.append(" ) WITH ( ");
        kafkaConfig.forEach((key, value) -> sb.append("'").append(key).append("' = '").append(value).append("', "));
        sb.append("'topic' = '").append(topic).append("', ");
        sb.append("'scan.startup.mode' ='").append(scanStartupMode).append("', ");
        sb.append("'value.format' = '").append(valueFormat).append("' ");
        sb.append(");");
        return sb.toString();
    }


}
