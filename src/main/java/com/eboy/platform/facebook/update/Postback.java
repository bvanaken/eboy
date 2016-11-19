package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Postback {

    @JsonProperty("payload")
    private String payload;

    public Postback() {
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Postback{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
