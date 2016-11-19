package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuickReply {

    @JsonProperty("payload")
    private String payload;

    public QuickReply() {
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "QuickReply{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
