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

    public String getValueAsString() { return value.toString(); }

    @Override
    public String toString() {
        return "Field{" +
                "value=" + value +
                '}';
    }
}
