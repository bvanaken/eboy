package com.eboy.conversation.outgoing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.eboy.platform.MessageType;

public class MessagePayload {

    @JsonProperty
    private String url;

    @JsonProperty
    private MessageType type;

    public MessagePayload() {
    }

    public String getUrl() {
        return url;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "MessagePayload{" +
                "url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
