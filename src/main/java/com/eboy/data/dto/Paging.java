package com.eboy.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paging {

    @JsonProperty("numFound")
    String entriesFound;

    public Paging() {
    }

    public String getEntriesFound() {
        return entriesFound;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "entriesFound='" + entriesFound + '\'' +
                '}';
    }
}
