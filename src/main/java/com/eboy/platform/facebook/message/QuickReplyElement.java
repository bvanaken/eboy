package com.eboy.platform.facebook.message;

public class QuickReplyElement {

    @JsonProperty("content_type")
    private String contentType;

    private String title;

    private String payload;

    @JsonProperty("image_url")
    private String imageUrl;

    private QuickReplyElement(String contentType, String title, String payload, String imageUrl) {
        this.contentType = contentType;
        this.title = title;
        this.payload = payload;
        this.imageUrl = imageUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public String getTitle() {
        return title;
    }

    public String getPayload() {
        return payload;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String toString() {
        return "QuickReplyElement{" +
                "contentType='" + contentType + '\'' +
                ", title='" + title + '\'' +
                ", payload='" + payload + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public static class Builder {

        private String contentType = "text";

        private String title;

        private String payload;

        private String imageUrl;

        public static Builder create() {
            return new Builder();
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withPayload(String payload) {
            this.payload = payload;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public QuickReplyElement build() {
            return new QuickReplyElement(contentType, title, payload, imageUrl);
        }

    }
}
