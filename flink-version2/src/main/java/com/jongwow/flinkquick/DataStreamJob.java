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

package com.jongwow.flinkquick;

import com.jongwow.flinkquick.data.Message;
import com.jongwow.flinkquick.data.json.JsonSchema;
import com.jongwow.flinkquick.data.kafka.KafkaStringRecord;
import com.jongwow.flinkquick.deserializer.KafkaDeserializationSchema;
import com.jongwow.flinkquick.processor.JsonProcessor;
import com.jongwow.flinkquick.transform.Transformation;
import com.jongwow.flinkquick.transform.TransformationFactory;
import com.jongwow.flinkquick.utils.JsonConverter;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

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
public class DataStreamJob {

    public static void main(String[] args) throws Exception {
        // Sets up the execution environment, which is the main entry point
        // to building Flink applications.
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        KafkaSource<KafkaStringRecord> source = getKafkaSource();
        DataStreamSource<KafkaStringRecord> kafkaStream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

//        Transformation<Message> transformation = (Transformation<Message>) TransformationFactory.getTransformation("dms", null);
        Transformation<Message> transformation = (Transformation<Message>) TransformationFactory.getTransformation("json", getJsonSchemaFromArgs());

        DataStream<Message> parseJson = kafkaStream
                .process(new JsonProcessor(transformation))
                .name("parse json");

        parseJson.print().name("kafka print");


        // Execute program, beginning computation.
        env.execute("Flink Java API Skeleton");
    }


    public static KafkaSource<KafkaStringRecord> getKafkaSource() {
        return KafkaSource.<KafkaStringRecord>builder()
                .setBootstrapServers("localhost:9095")
                .setTopics("test-json-topic")
                .setGroupId("my-group")
                .setProperty("security.protocol", "SASL_PLAINTEXT")
                .setProperty("sasl.mechanism", "PLAIN")
                .setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"user1\" password=\"bYYnBX7ITw\";")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setDeserializer(new KafkaDeserializationSchema())
                .build();
    }

    //TODO: from args or env or external?
    public static JsonSchema getJsonSchemaFromArgs() {
        JsonSchema jsonSchema = new JsonSchema("user_activity");
        List<String> columns = new ArrayList<>();
        // sample
        columns.add("user_id NUMBER");
        columns.add("item_id NUMBER");
        columns.add("behavior STRING");
        jsonSchema.columns = JsonConverter.convertJsonColumns(columns);
        return jsonSchema;
    }
}
