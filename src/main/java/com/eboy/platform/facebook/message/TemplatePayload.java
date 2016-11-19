package com.eboy.platform.facebook.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TemplatePayload {

    @JsonProperty("template_type")
    private String templateType = "generic";

    private List<PayloadElement> elements;

    private TemplatePayload(List<PayloadElement> elements) {
        this.elements = elements;
    }

    public String getTemplateType() {
        return templateType;
    }

    public List<PayloadElement> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        return "TemplatePayload{" +
                "templateType='" + templateType + '\'' +
                ", elements=" + elements +
                '}';
    }

    public static class Builder {

        private List<PayloadElement> elements;

        public static Builder create() {
            return new Builder();
        }

        public Builder withElements(List<PayloadElement> elements) {
            this.elements = elements;
            return this;
        }

        public TemplatePayload build() {
            return new TemplatePayload(elements);
        }
    }
}
