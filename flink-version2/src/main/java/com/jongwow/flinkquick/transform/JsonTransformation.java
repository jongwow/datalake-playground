package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.JsonMessage;
import com.jongwow.flinkquick.data.json.JsonDataType;
import com.jongwow.flinkquick.data.json.JsonSchema;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformation implements Transformation<JsonMessage>{
    private static final long serialVersionUID = 1L;

    private JsonSchema jsonSchema;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonTransformation(JsonSchema jsonSchema) {
        this.jsonSchema = jsonSchema;
    }

    @Override
    public JsonMessage transform(String raw) throws Exception {
        JsonMessage jsonMessage = new JsonMessage();
        JsonNode rootNode = objectMapper.readTree(raw);
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
