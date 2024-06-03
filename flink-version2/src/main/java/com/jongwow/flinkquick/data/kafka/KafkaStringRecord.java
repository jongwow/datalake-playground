package com.jongwow.flinkquick.data.kafka;

import java.io.Serializable;

public class KafkaStringRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    private long logAppendTime;
    private String value;

    public KafkaStringRecord(long logAppendTime, String value) {
        this.logAppendTime = logAppendTime;
        this.value = value;
    }

    public long getLogAppendTime() {
        return logAppendTime;
    }

    public String getValue() {
        return value;
    }
}

