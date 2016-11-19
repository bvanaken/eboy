package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AdsValue {

    @JsonProperty("ad")
    List<Ad> ads;

    @JsonProperty
    Paging paging;

    public AdsValue() {
    }

    public List<Ad> getAds() {
        return ads;
    }

    public Paging getPaging() {
        return paging;
    }

    @Override
    public String toString() {
        return "AdsValue{" +
                "ads=" + ads +
                ", paging=" + paging +
                '}';
    }
}
