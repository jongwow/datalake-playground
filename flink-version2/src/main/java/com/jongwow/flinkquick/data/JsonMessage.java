package com.jongwow.flinkquick.data;

import java.util.HashMap;
import java.util.Map;

public class JsonMessage extends Message {
    //TODO: inject schema
    // 그 구현과 관리는 고민.
    private final Map<String, Object> data = new HashMap<>();

    public JsonMessage() {
    }

    public void addField(String key, Object value){
        this.data.put(key, value);
    }

    @Override
    public String toString() {
        return "Json" + this.data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JsonMessage that = (JsonMessage) o;
        return this.data.equals(that.data);
    }
}
