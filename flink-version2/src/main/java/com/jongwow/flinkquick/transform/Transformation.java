package com.jongwow.flinkquick.transform;

import com.jongwow.flinkquick.data.Message;
import com.jongwow.flinkquick.data.kafka.KafkaStringRecord;

import java.io.Serializable;

public interface Transformation<M extends Message> extends Serializable {
    M transform(KafkaStringRecord raw) throws Exception;
}

// 과연 serializable 한게 좋을 것인가?