package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdsObject {

    @JsonProperty("value")
    AdsValue object;

    public AdsObject() {
    }

    public AdsValue getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "AdsObject{" +
                "object=" + object +
                '}';
    }
}
