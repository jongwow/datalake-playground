package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.JsonMessage;
import com.jongwow.flinkquick.data.json.JsonDataType;
import com.jongwow.flinkquick.data.json.JsonSchema;
import com.jongwow.flinkquick.data.kafka.KafkaStringRecord;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformation implements Transformation<JsonMessage>{
    private static final long serialVersionUID = 1L;

    private final JsonSchema jsonSchema;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonTransformation(JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    @Override
    public JsonMessage transform(KafkaStringRecord raw) throws Exception {
        JsonMessage jsonMessage = new JsonMessage();
        JsonNode rootNode = objectMapper.readTree(raw.getValue());
        //TODO: 개선하기
        jsonMessage.addField("reg_ts", raw.getLogAppendTime());
        jsonSchema.columns.forEach(jsonColumn -> {
            JsonNode field = rootNode.get(jsonColumn.getColumnName());
            if (jsonColumn.getDataType() == JsonDataType.BIGINT) {
                int anInt = field.asInt();
                jsonMessage.addField(jsonColumn.getColumnName(), anInt);
            }
            if (jsonColumn.getDataType() == JsonDataType.VARCHAR) {
                String text = field.asText();
                jsonMessage.addField(jsonColumn.getColumnName(), text);
            }
        });
        return jsonMessage;
    }

}
