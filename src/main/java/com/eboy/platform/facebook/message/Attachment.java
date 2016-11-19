package com.eboy.platform.facebook.message;

import com.eboy.platform.MessageType;

public class Attachment {

    private Object payload;
    private String type;

    private Attachment(Object payload, String type) {
        this.payload = payload;
        this.type = type;
    }

    public Object getPayload() {
        return payload;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "payload=" + payload +
                ", type='" + type + '\'' +
                '}';
    }

    public static class Builder {

        private Object payload;
        private MessageType type;

        public static Builder create() {
            return new Builder();
        }

        public Builder withPayload(Object payload) {
            this.payload = payload;
            return this;
        }

        public Builder withType(MessageType type) {
            this.type = type;
            return this;
        }

        public Attachment build() {

            switch (type) {
                case IMAGE:
                    return new Attachment(payload, "image");
                case VIDEO:
                    return new Attachment(payload, "video");
                case TEMPLATE:
                    return new Attachment(payload, "template");
                default:
                    return null;
            }
        }
    }
}
