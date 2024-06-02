/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jongwow.flinkapp;

import com.jongwow.flinkapp.sql.SourceKafkaTableDefinition;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.*;
import org.apache.flink.types.Row;

/**
 * Skeleton for a Flink DataStream Job.
 *
 * <p>For a tutorial how to write a Flink application, check the
 * tutorials and examples on the <a href="https://flink.apache.org">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingJob {

    public static void main(String[] args) throws Exception {
        // Sets up the execution environment, which is the main entry point
        // to building Flink applications.
        ParameterTool parameters = ParameterTool.fromArgs(args);
        String kafkaPassword = parameters.get("kafka-password", "ynYIrsvqbn");
        String kafkaUsername = parameters.get("kafka-username", "user1");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.getConfig().setGlobalJobParameters(parameters);
        TableEnvironment tableEnv = StreamTableEnvironment.create(env);


        tableEnv.executeSql(getSourceTableSQL(kafkaUsername, kafkaPassword));
        tableEnv.executeSql("SELECT user_id, item_id FROM SourceKafkaTable").print();
    }

    public static String getSourceTableSQL(String kafkaJaasUsername, String kafkaJaasPassword) {
        SourceKafkaTableDefinition tableApi = new SourceKafkaTableDefinition("SourceKafkaTable");
        String kafkaJaasConfigModule = "org.apache.kafka.common.security.plain.PlainLoginModule";
        String kafkaGroupId = "testGroup";
        String scanStartupMode = "earliest-offset";
        tableApi.addColumn("event_time", "TIMESTAMP(3)", "FROM 'timestamp'");
        tableApi.addColumn("partition", "BIGINT", "VIRTUAL");
        tableApi.addColumn("offset", "BIGINT", "VIRTUAL");
        tableApi.addColumn("user_id", "BIGINT", null);
        tableApi.addColumn("item_id", "BIGINT", null);
        tableApi.addColumn("behavior", "STRING", null);
        tableApi.setScanStartupMode(scanStartupMode);
        tableApi.setTopic("user_behavior");
        tableApi.setValueFormat("json");
        tableApi.setKafkaConfig("connector", "kafka");
        tableApi.setKafkaConfig("properties.bootstrap.servers", "kafka:9092");
        tableApi.setKafkaConfig("properties.group.id", kafkaGroupId);
        tableApi.setKafkaConfig("properties.sasl.jaas.config", kafkaJaasConfigModule+" required username=\"" + kafkaJaasUsername + "\" password=\"" + kafkaJaasPassword + "\";");
        tableApi.setKafkaConfig("properties.sasl.mechanism", "PLAIN");
        tableApi.setKafkaConfig("properties.security.protocol", "SASL_PLAINTEXT");
        return tableApi.build();
    }
}



