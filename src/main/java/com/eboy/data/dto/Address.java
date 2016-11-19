package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty
    Field longitude;

    @JsonProperty
    Field latitude;

    public Address() {
    }

    public Field getLongitude() {
        return longitude;
    }

    public Field getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Address{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}

