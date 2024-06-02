package com.jongwow.flinkquick.processor;

import com.jongwow.flinkquick.data.DmsMessage;
import com.jongwow.flinkquick.data.Message;
import com.jongwow.flinkquick.transform.DmsTransformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.junit.Test;
import org.mockito.Mockito;

public class JsonProcessorTest {

    private final ProcessFunction<String, Message>.Context contextMock = Mockito.mock(ProcessFunction.Context.class);
    private final Collector<Message> collectorMock = Mockito.mock(Collector.class);

    private JsonProcessor jsonProcessor;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testProcessElement_should() throws Exception {
        // given
        jsonProcessor = new JsonProcessor(new DmsTransformation());
        String raw = "{\n" +
                "\t\"data\": {\n" +
                "\t\t\"id\": 2\n" +
                "\t},\n" +
                "\t\"metadata\": {\n" +
                "\t\t\"key\": \"foo\"\n" +
                "\t}\n" +
                "}";

        // when
        jsonProcessor.processElement(raw, contextMock, collectorMock);

        // then
        DmsMessage dmsMessage = new DmsMessage();
        JsonNode jsonNode = objectMapper.readTree(raw);
        dmsMessage.setData(jsonNode.get("data"));

        Mockito.verify(collectorMock).collect(dmsMessage);
    }
}