package com.jongwow.flinkquick.data;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonNode;

public final class DmsMessage extends Message {
    private JsonNode data;
    private JsonNode metadata;


    public DmsMessage() {
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DmsMessage{data=" + this.data + "}";
    }

    public void setMetadata(JsonNode metaObject) {
        this.metadata = metaObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            DmsMessage dm = (DmsMessage)o;
            return toString().equals(dm.toString());
        } else {
            return false;
        }

    }
}
