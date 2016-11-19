package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Link {

    @JsonProperty
    String href;

    @JsonProperty
    String rel;

    public Link() {
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    @Override
    public String toString() {
        return "Link{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                '}';
    }
}
