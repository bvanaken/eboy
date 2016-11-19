package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {

    @JsonProperty
    Object value;

    public Field() {
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Field{" +
                "value=" + value +
                '}';
    }
}
