package com.eboy.platform.facebook.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PayloadElement {

    private String title;

    @JsonProperty("subtitle")
    private String subTitle;

    @JsonProperty("image_url")
    private String imageUrl;

    private List<Button> buttons;

    private PayloadElement(String title, String subTitle, String imageUrl, List<Button> buttons) {
        this.title = title;
        this.subTitle = subTitle;
        this.imageUrl = imageUrl;
        this.buttons = buttons;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    @Override
    public String toString() {
        return "PayloadElement{" +
                "title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", buttons=" + buttons +
                '}';
    }

    public static class Builder {
        private String title;
        private String subTitle;
        private String imageUrl;
        private List<Button> buttons;

        public static Builder create() {
            return new Builder();
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSubTitle(String subTitle) {
            this.subTitle = subTitle;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder withButtons(List<Button> buttons) {
            this.buttons = buttons;
            return this;
        }

        public PayloadElement build() {
            return new PayloadElement(title, subTitle, imageUrl, buttons);
        }

    }
}
