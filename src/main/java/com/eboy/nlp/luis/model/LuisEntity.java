package com.eboy.nlp.luis.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LuisEntity {

    @JsonProperty
    private String entity;

    @JsonProperty
    private String type;

    @JsonProperty
    private Float score;

    public LuisEntity() {
    }

    public String getEntity() {
        return entity;
    }

    public String getType() {
        return type;
    }

    public Float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "LuisEntity{" +
                "entity='" + entity + '\'' +
                ", type='" + type + '\'' +
                ", score=" + score +
                '}';
    }
}
