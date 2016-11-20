package com.eboy.mv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by root1 on 20/11/16.
 */
public class Recognition {
    public Category[] categories;
    public String requestId;

    @JsonProperty("metadata")
    public MetaData metaData;

    public Recognition() {
    }

    public Recognition(Category[] categories, String requestId, MetaData metaData) {
        this.categories = categories;
        this.requestId = requestId;
        this.metaData = metaData;
    }

}
