package com.jongwow.flinkapp.sql;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SourceKafkaTableDefinitionTest {
    @Test
    public void testTableCreation() {
        SourceKafkaTableDefinition tableApi = new SourceKafkaTableDefinition("SourceKafkaTable");
        tableApi.addColumn("event_time", "TIMESTAMP(3)", "FROM 'timestamp'");
        tableApi.addColumn("partition", "BIGINT", "VIRTUAL");
        tableApi.addColumn("offset", "BIGINT", "VIRTUAL");
        tableApi.addColumn("user_id", "BIGINT", null);
        tableApi.addColumn("item_id", "BIGINT", null);
        tableApi.addColumn("behavior", "STRING", null);
        tableApi.setTopic("user_behavior");
        tableApi.setValueFormat("json");
        tableApi.setKafkaConfig("connector", "kafka");
        tableApi.setKafkaConfig("properties.bootstrap.servers", "kafka:9092");
        tableApi.setKafkaConfig("properties.group.id", "testGroup");
        tableApi.setKafkaConfig("properties.sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"user1\" password=\"mrC8MBZga1\";");
        tableApi.setKafkaConfig("properties.sasl.mechanism", "PLAIN");
        tableApi.setKafkaConfig("properties.security.protocol", "SASL_PLAINTEXT");

        String expectedQuery = "CREATE TABLE SourceKafkaTable ( " +
                "`event_time` TIMESTAMP(3) METADATA FROM 'timestamp', " +
                "`partition` BIGINT METADATA VIRTUAL, " +
                "`offset` BIGINT METADATA VIRTUAL, " +
                "`user_id` BIGINT, " +
                "`item_id` BIGINT, " +
                "`behavior` STRING " +
                ") WITH ( " +
                "'connector' = 'kafka', " +
                "'properties.bootstrap.servers' = 'kafka:9092', " +
                "'properties.group.id' = 'testGroup', " +
                "'properties.sasl.jaas.config' = 'org.apache.kafka.common.security.plain.PlainLoginModule required username=\"user1\" password=\"mrC8MBZga1\";', " +
                "'properties.sasl.mechanism' = 'PLAIN', " +
                "'properties.security.protocol' = 'SASL_PLAINTEXT', " +
                "'topic' = 'user_behavior', " +
                "'value.format' = 'json' " +
                ");";

        assertEquals(expectedQuery, tableApi.build());
    }
}