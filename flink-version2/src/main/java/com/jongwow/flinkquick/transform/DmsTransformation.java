package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.DmsMessage;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class DmsTransformation implements Transformation<DmsMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public DmsMessage transform(String raw) throws Exception {
        JsonNode jsonNode = objectMapper.readTree(raw);
        JsonNode dataObject = jsonNode.get("data");
        JsonNode metaObject = jsonNode.get("metadata");
        //TODO: 어차피 json 이니까 check method 를 정의해서 transform 에서 구현하기
        // - 근데 dms 에서도 data와 metadata 체크는 필요함.
        if (metaObject == null || dataObject == null) {
            throw new Exception("Not Json");
        }
        DmsMessage dmsMessage = new DmsMessage();
        dmsMessage.setData(dataObject);
        dmsMessage.setMetadata(metaObject);
        return dmsMessage;
    }
}
