package com.eboy.conversation.outgoing.dto;

import com.eboy.nlp.Intent;

public class Button {

    private ButtonType type;
    private String title;
    private String url;
    private String intent;

    private Button(ButtonType type, String title, String url, String intent) {
        this.type = type;
        this.title = title;
        this.url = url;
        this.intent = intent;
    }

    public ButtonType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getIntent() {
        return intent;
    }

    public static class Builder {
        private ButtonType type;
        private String title;
        private String url;
        private String intent;

        public static Builder create() {
            return new Builder();
        }

        public Builder withType(ButtonType type) {
            this.type = type;
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

        public Builder withIntent(Intent intent) {
            if (intent != null) {
                this.intent = intent.name();
            }
            return this;
        }

        public Button build() {
            return new Button(type, title, url, intent);
        }
    }
}
