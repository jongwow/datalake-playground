package com.jongwow.flinkquick.data;

public class JsonMessage extends Message {
    //TODO: inject schema
    // 그 구현과 관리는 고민.

    public JsonMessage(int userId, int itemId, String behavior) {
        this.userId = userId;
        this.itemId = itemId;
        this.behavior = behavior;
    }

    int userId;
    int itemId;

    String behavior;

    @Override
    public String toString() {
        return "Json{" + userId + "," + itemId + "," + behavior + "}";
    }
}
