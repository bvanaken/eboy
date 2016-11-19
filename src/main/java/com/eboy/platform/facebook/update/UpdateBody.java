package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateBody {

    @JsonProperty("object")
    private String object;
    @JsonProperty("entry")
    private List<Entry> entries = new ArrayList<Entry>();

    public UpdateBody() {
    }

    public UpdateBody(String object, List<Entry> entries) {
        this.object = object;
        this.entries = entries;
    }

    public String getObject() {
        return object;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "UpdateBody{" +
                "object='" + object + '\'' +
                ", entries=" + entries +
                '}';
    }
}
