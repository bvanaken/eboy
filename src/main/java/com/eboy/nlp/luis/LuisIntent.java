package com.eboy.nlp.luis;

import com.eboy.nlp.Intent;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LuisIntent {

    @JsonProperty
    private Intent intent;

    @JsonProperty
    private Float score;

    public LuisIntent() {
    }

    public Intent getIntent() {
        return intent;
    }

    public Float getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "LuisIntent{" +
                "intent=" + intent +
                ", score=" + score +
                '}';
    }
}
