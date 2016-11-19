package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdsObject {

    @JsonProperty("value")
    AdsValue adsValue;

    public AdsObject() {
    }

    public AdsValue getAdsValue() {
        return adsValue;
    }

    @Override
    public String toString() {
        return "AdsObject{" +
                "adsValue=" + adsValue +
                '}';
    }
}
