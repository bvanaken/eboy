package com.eboy.platform.facebook.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public abstract class BaseMessage {

    @JsonProperty("quick_replies")
    List<QuickReplyElement> quickReplies;

    public BaseMessage(List<QuickReplyElement> quickReplies) {
        this.quickReplies = quickReplies;
    }

    public List<QuickReplyElement> getQuickReplies() {
        return quickReplies;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "quickReplies=" + quickReplies +
                '}';
    }
}
