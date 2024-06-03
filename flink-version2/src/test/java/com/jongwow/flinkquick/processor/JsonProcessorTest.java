package com.jongwow.flinkquick.processor;

import com.jongwow.flinkquick.data.DmsMessage;
import com.jongwow.flinkquick.data.JsonMessage;
import com.jongwow.flinkquick.data.Message;
import com.jongwow.flinkquick.data.json.JsonDataType;
import com.jongwow.flinkquick.data.json.JsonColumn;
import com.jongwow.flinkquick.utils.JsonConverter;
import com.jongwow.flinkquick.data.json.JsonSchema;
import com.jongwow.flinkquick.transform.DmsTransformation;
import com.jongwow.flinkquick.transform.JsonTransformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JsonProcessorTest {

    private final ProcessFunction<String, Message>.Context contextMock = Mockito.mock(ProcessFunction.Context.class);
    private final Collector<Message> collectorMock = Mockito.mock(Collector.class);

    private JsonProcessor jsonProcessor;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testProcessElementOfJson_should() throws Exception {
        JsonSchema jsonSchema = new JsonSchema();
        jsonSchema.tableName = "user_activity";
        jsonSchema.columns = JsonConverter.convertJsonColumns(getValidRawColumns());
        jsonProcessor = new JsonProcessor(new JsonTransformation(jsonSchema));
        String raw = "{\n" +
                "    \"user_id\": 123,\n" +
                "    \"item_id\": 235,\n" +
                "    \"behavior\": \"login\"\n" +
                "}";

        // when
        jsonProcessor.processElement(raw, contextMock, collectorMock);

        // then
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.addField("user_id", 123);
        jsonMessage.addField("item_id", 235);
        jsonMessage.addField("behavior", "login");

        Mockito.verify(collectorMock).collect(jsonMessage);
    }

    @Test
    public void testProcessElementOfJson_shouldNotEqual() throws Exception {
        JsonSchema jsonSchema = new JsonSchema();
        jsonSchema.tableName = "user_activity";
        jsonSchema.columns = JsonConverter.convertJsonColumns(getValidRawColumns());
        jsonProcessor = new JsonProcessor(new JsonTransformation(jsonSchema));
        String raw = "{\n" +
                "    \"user_id\": 123,\n" +
                "    \"item_id\": 235,\n" +
                "    \"behavior\": \"LOGOUT\"\n" +
                "}";

        // when
        jsonProcessor.processElement(raw, contextMock, collectorMock);

        // then
        JsonMessage expected = new JsonMessage();
        expected.addField("user_id", 123);
        expected.addField("item_id", 235);
        expected.addField("behavior", "LOGIN"); // This is different

        ArgumentCaptor<JsonMessage> captor = ArgumentCaptor.forClass(JsonMessage.class);
        Mockito.verify(collectorMock).collect(captor.capture());
        JsonMessage actual = captor.getValue();

        assertThat(actual).isNotEqualTo(expected);
    }

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

    @Test
    public void testJsonSchema() {
        // given
        List<String> columns = new ArrayList<>();
        // JSON 의 data type 은 string, number, array, boolean, null, Object 를 갖는다.
        columns.add("user_id NUMBER");
        columns.add("item_id NumBer");
        columns.add("behavior STRING");
        // when
        List<JsonColumn> collect = columns.stream().map(JsonConverter::convertJsonColumn).collect(Collectors.toList());
        // then
        List<JsonColumn> expected = new ArrayList<>();
        expected.add(new JsonColumn("user_id", JsonDataType.BIGINT));
        expected.add(new JsonColumn("item_id", JsonDataType.BIGINT));
        expected.add(new JsonColumn("behavior", JsonDataType.VARCHAR));

        assertThat(collect).isEqualTo(expected);
    }

    @Test
    public void testJsonSchema_ThrowIllegalError() {
        // given
        List<String> columns = new ArrayList<>();
        // JSON 의 data type 은 string, number, array, boolean, null, Object 를 갖는다.
        columns.add("user_id NUMBER");
        columns.add("item_id integer");
        columns.add("behavior STRING");

        // when & then
        assertThatThrownBy(() -> JsonConverter.convertJsonColumns(columns))
                .isInstanceOf(IllegalArgumentException.class);
    }
    public static List<String> getValidRawColumns(){
        List<String> columns = new ArrayList<>();
        columns.add("user_id NUMBER");
        columns.add("item_id Number");
        columns.add("behavior STRING");
        return columns;
    }

}