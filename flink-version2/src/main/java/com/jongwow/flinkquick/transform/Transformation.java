package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.Message;

public interface Transformation<M extends Message> {
    public M transform(String raw) throws Exception;
}
