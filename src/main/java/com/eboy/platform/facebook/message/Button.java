package com.eboy.platform.facebook.message;

import com.eboy.conversation.outgoing.dto.ButtonType;

public class Button {

    private String type;
    private String title;
    private String url;
    private String payload;

    private Button(String type, String title, String url, String payload) {
        this.type = type;
        this.title = title;
        this.url = url;
        this.payload = payload;
    }

    public String getTitle() {
        return title;
    }

    public String getPayload() {
        return payload;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Button{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }

    public static class Builder {
        private String type;
        private String title;
        private String url;
        private String payload;

        public static Builder create() {
            return new Builder();
        }

        public Builder ofType(ButtonType type) {

            switch (type) {
                case INTENT:
                    this.type = "postback";
                    break;
                case URL:
                    this.type = "web_url";
                    break;
                case LOGIN:
                    this.type = "account_link";
                    break;
                case SHARE:
                    this.type = "element_share";
                    break;
            }

            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withPayload(String payload) {
            this.payload = payload;
            return this;
        }

        public Button build() {
            return new Button(type, title, url, payload);
        }
    }
}
