package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.DmsMessage;
import com.jongwow.flinkquick.data.JsonMessage;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTransformation implements Transformation<JsonMessage>{

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public JsonMessage transform(String raw) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(raw);
        int userId = jsonNode.get("user_id").asInt();
        int itemId = jsonNode.get("item_id").asInt();
        String behavior = jsonNode.get("behavior").asText();
        return new JsonMessage(
                userId,
                itemId,
                behavior
        );
    }
}
