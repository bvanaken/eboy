package com.eboy.platform.facebook.message;

import java.util.List;

public class AttachmentMessage extends BaseMessage {

    private Attachment attachment;

    private AttachmentMessage(Attachment attachment, List<QuickReplyElement> quickReplies, String metaData) {
        super(quickReplies);
        this.attachment = attachment;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    @Override
    public String toString() {
        return "AttachmentMessage{" +
                "attachment=" + attachment +
                '}';
    }

    public static class Builder {

        private Attachment attachment;
        private List<QuickReplyElement> quickReplies;
        private String metaData;

        public static Builder create() {
            return new Builder();
        }

        public Builder withAttachment(Attachment attachment) {
            this.attachment = attachment;
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

        public AttachmentMessage build() {
            return new AttachmentMessage(attachment, quickReplies, metaData);
        }
    }
}
