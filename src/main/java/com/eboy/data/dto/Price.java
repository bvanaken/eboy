package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Price {

    @JsonProperty
    Field amount;

    public Price() {
    }

    public Field getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Price{" +
                "amount=" + amount +
                '}';
    }
}
