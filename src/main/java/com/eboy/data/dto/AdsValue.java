package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class AdsValue {

    @JsonProperty("ad")
    ArrayList<Ad> ads;

    @JsonProperty
    Paging paging;

    public AdsValue() {
    }

    public ArrayList<Ad> getAds() {
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
