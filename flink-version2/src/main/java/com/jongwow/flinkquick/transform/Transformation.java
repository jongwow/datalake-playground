package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.Message;

import java.io.Serializable;

public interface Transformation<M extends Message> extends Serializable {
    public M transform(String raw) throws Exception;
}

// 과연 serializable 한게 좋을 것인가?