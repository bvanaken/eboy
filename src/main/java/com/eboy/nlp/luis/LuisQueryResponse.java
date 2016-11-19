package com.eboy.nlp.luis;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LuisQueryResponse {

    @JsonProperty
    private String query;

    @JsonProperty("topScoringIntent")
    private LuisIntent topIntent;

    @JsonProperty
    private List<LuisEntity> entities;

    public LuisQueryResponse() {
    }

    public String getQuery() {
        return query;
    }

    public LuisIntent getTopIntent() {
        return topIntent;
    }

    public List<LuisEntity> getEntities() {
        return entities;
    }

    @Override
    public String toString() {
        return "LuisQueryResponse{" +
                "query='" + query + '\'' +
                ", topIntent=" + topIntent +
                ", entities=" + entities +
                '}';
    }
}
