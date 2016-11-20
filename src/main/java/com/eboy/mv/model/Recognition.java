package com.eboy.mv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by root1 on 20/11/16.
 */
public class Recognition {
    public List<Tag> tags;
    public String requestId;

    @JsonProperty("metadata")
    public MetaData metaData;

    public Recognition() {
    }

    public Recognition(List<Tag> tags, String requestId, MetaData metaData) {
        this.tags = tags;
        this.requestId = requestId;
        this.metaData = metaData;
    }

}
