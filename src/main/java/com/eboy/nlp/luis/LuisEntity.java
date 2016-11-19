package com.eboy.nlp.luis;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LuisEntity {

    @JsonProperty
    private String entity;

    @JsonProperty
    private String type;

    @JsonProperty
    private Long score;

    public LuisEntity() {
    }

    public String getEntity() {
        return entity;
    }

    public String getType() {
        return type;
    }

    public Long getScore() {
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
