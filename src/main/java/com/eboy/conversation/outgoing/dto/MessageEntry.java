package com.eboy.conversation.outgoing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageEntry {

    @JsonProperty
    private String text;

    @JsonProperty
    private MessagePayload payload;

    public MessageEntry() {
    }

    public String getText() {
        return text;
    }

    public MessagePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "MessageEntry{" +
                "text='" + text + '\'' +
                ", payload=" + payload +
                '}';
    }
}
