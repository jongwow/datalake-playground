package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.Message;

public class TransformationFactory {
    public static Transformation<? extends Message> getTransformation(String type){
        if ("dms".equals(type)) {
            return new DmsTransformation();
        } else if ("json".equals(type)) {
            return new JsonTransformation();
        }
        throw new IllegalArgumentException("Invalid type: "+type);
    }
}
