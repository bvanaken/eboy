package com.eboy.nlp.luis;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.eboy.nlp.Intent;

public class LuisIntent {

    @JsonProperty
    private Intent intent;

    @JsonProperty
    private Long score;

    public LuisIntent() {
    }

    public Intent getIntent() {
        return intent;
    }

    public Long getScore() {
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
