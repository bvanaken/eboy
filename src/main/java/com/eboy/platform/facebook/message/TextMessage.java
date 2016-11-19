package com.eboy.platform.facebook.message;

import java.util.List;

public class TextMessage extends BaseMessage {

    private String text;

    private TextMessage(String text, List<QuickReplyElement> quickReplies, String metaData) {
        super(quickReplies);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "text='" + text + '\'' +
                ", quickReplies=" + quickReplies +
                '}';
    }

    public static class Builder {
        private String text;
        private List<QuickReplyElement> quickReplies;
        private String metaData;

        public static Builder create() {
            return new Builder();
        }

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withQuickReplies(List<QuickReplyElement> quickReplies) {
            this.quickReplies = quickReplies;
            return this;
        }

        public Builder withMetaData(String metaData) {
            this.metaData = metaData;
            return this;
        }

        public TextMessage build() {
            return new TextMessage(text, quickReplies, metaData);
        }
    }
}
