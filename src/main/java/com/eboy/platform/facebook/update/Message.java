
package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    @JsonProperty("mid")
    private String mid;

    @JsonProperty("seq")
    private Long seq;

    @JsonProperty("text")
    private String text;

    @JsonProperty("quick_reply")
    private QuickReply quickReply;

    public Message() {
    }

    public String getMid() {
        return mid;
    }

    public Long getSeq() {
        return seq;
    }

    public String getText() {
        return text;
    }

    public QuickReply getQuickReply() {
        return quickReply;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", quickReply=" + quickReply +
                '}';
    }
}
