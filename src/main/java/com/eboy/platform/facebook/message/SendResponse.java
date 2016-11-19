package com.eboy.platform.facebook.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class SendResponse implements Serializable {

    @JsonProperty("message_id")
    private String messageId;

    @JsonProperty("recipient_id")
    private String recipientId;

    public SendResponse() {
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    @Override
    public String toString() {
        return "SendResponse{" +
                "messageId='" + messageId + '\'' +
                ", recipientId='" + recipientId + '\'' +
                '}';
    }
}
