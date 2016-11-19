package com.eboy.platform.facebook.message;

public class AttachmentPayload {

    private String url;

    private AttachmentPayload(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "AttachmentPayload{" +
                "url='" + url + '\'' +
                '}';
    }

    public static class Builder {
        private String url;

        public static Builder create() {
            return new Builder();
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public AttachmentPayload build() {
            return new AttachmentPayload(url);
        }
    }
}
