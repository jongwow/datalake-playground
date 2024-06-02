package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.Message;
import com.jongwow.flinkquick.data.json.JsonSchema;

public class TransformationFactory {
    //TODO: parameters를 config로 바꾸면 주입 쉬울 듯.
    public static Transformation<? extends Message> getTransformation(String type, JsonSchema jsonSchema){
        if ("dms".equals(type)) {
            return new DmsTransformation();
        } else if ("json".equals(type)) {
            return new JsonTransformation(jsonSchema);
        }
        throw new IllegalArgumentException("Invalid type: "+type);
    }

}
