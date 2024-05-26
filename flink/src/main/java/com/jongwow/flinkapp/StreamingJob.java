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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jongwow.flinkapp.config.SourceColumn;
import com.jongwow.flinkapp.config.SourceKafkaConfig;
import com.jongwow.flinkapp.sql.SourceKafkaTableDefinition;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.*;
import org.apache.flink.table.api.bridge.java.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import static jdk.internal.org.jline.utils.InfoCmp.Capability.columns;

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
        ParameterTool fileParams = ParameterTool.fromPropertiesFile("/etc/config/config.properties");

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.getConfig().setGlobalJobParameters(fileParams);
        TableEnvironment tableEnv = StreamTableEnvironment.create(env);


        SourceKafkaConfig sourceConfig = getSourceConfig(fileParams);
        tableEnv.executeSql(getSourceTableSql2(sourceConfig));
        tableEnv.executeSql("SELECT user_id, item_id FROM SourceKafkaTable").print();
    }

    public static SourceKafkaConfig getSourceConfig(ParameterTool params){
        String SOURCE = "source.";
        Properties properties = params.getProperties();
        SourceKafkaConfig config = new SourceKafkaConfig();

        String connector = properties.getProperty(SOURCE + "connector");
        String topic = properties.getProperty(SOURCE + "topic");
        String scanStartupMode = properties.getProperty(SOURCE + "scan.startup.mode");
        String kafkaGroupId = properties.getProperty(SOURCE + "group.id");
        String valueFormat = properties.getProperty(SOURCE + "value.format");
        String valueIgnoreError = properties.getProperty(SOURCE + "value.json.ignore-parse-errors");
        String saslJaasConfig = properties.getProperty(SOURCE + "properties.sasl.jaas.config");
        String securityProtocol = properties.getProperty(SOURCE + "properties.security.protocol");
        String saslJaasMechanism = properties.getProperty(SOURCE + "properties.sasl.mechanism");
        String bootstrapServers = properties.getProperty(SOURCE + "properties.bootstrap.servers");

        System.out.println("saslJaasConfig: "+saslJaasConfig);


        config.setConnector(connector);
        config.setSaslMechanism(saslJaasMechanism);
        config.setBootStrapServers(bootstrapServers);
        config.setSaslJaasConfig(saslJaasConfig);
        config.setGroupId(kafkaGroupId);
        config.setScanStartupMode(scanStartupMode);
        config.setTopicName(topic);
        config.setValueFormat(valueFormat);
        config.setValueJsonIgnoreParseError(valueIgnoreError);
        config.setSecurityProtocol(securityProtocol);

        config.addColumn("event_time", "TIMESTAMP(3)", "FROM 'timestamp'");
        config.addColumn("partition", "BIGINT", "VIRTUAL");
        config.addColumn("offset", "BIGINT", "VIRTUAL");
        config.addColumn("user_id", "BIGINT", null);
        config.addColumn("item_id", "BIGINT", null);
        config.addColumn("behavior", "STRING", null);

        return config;
    }

    public static String getSourceTableSql2(SourceKafkaConfig config){
        SourceKafkaTableDefinition tableApi = new SourceKafkaTableDefinition("SourceKafkaTable");

        List<SourceColumn> columns = config.getColumns();
        columns.forEach(c -> {
            tableApi.addColumn(c.name, c.type, c.expression);
        });

        tableApi.setScanStartupMode(config.getScanStartupMode());
        tableApi.setTopic(config.getTopicName());
        tableApi.setValueFormat(config.getValueFormat());
        tableApi.setKafkaConfig("connector", config.getConnector());
        tableApi.setKafkaConfig("properties.bootstrap.servers", config.getBootStrapServers());
        tableApi.setKafkaConfig("properties.group.id", config.getGroupId());
        tableApi.setKafkaConfig("properties.sasl.jaas.config", config.getSaslJaasConfig());
        tableApi.setKafkaConfig("properties.sasl.mechanism", config.getSaslMechanism());
        tableApi.setKafkaConfig("properties.security.protocol", config.getSecurityProtocol());
        tableApi.setKafkaConfig("value.json.ignore-parse-errors", config.getValueJsonIgnoreParseError());
        return tableApi.build();
    }
}