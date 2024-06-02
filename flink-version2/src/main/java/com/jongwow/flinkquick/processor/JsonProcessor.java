package com.jongwow.flinkquick.processor;

import com.jongwow.flinkquick.data.DmsMessage;

import com.jongwow.flinkquick.data.Message;
import com.jongwow.flinkquick.transform.Transformation;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonProcessor extends ProcessFunction<String, Message> {
    private static final long serialVersionUID = 1L;
    private final Transformation<? extends Message> transformation;

    private transient ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(JsonProcessor.class);

    public JsonProcessor(Transformation<? extends Message> transformation) {
        super();
        this.transformation = transformation;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
    }

    @Override
    public void processElement(String raw, ProcessFunction<String, Message>.Context context, Collector<Message> collector) throws Exception {
        try {
            Message transform = transformation.transform(raw);
            collector.collect(transform);
        } catch (Exception e) {
            LOG.warn("Kafka Message parse Error: {}", e.getMessage());
        }
    }
}
