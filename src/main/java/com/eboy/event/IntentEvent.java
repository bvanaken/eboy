package com.eboy.event;

import com.eboy.nlp.luis.model.LuisQueryResponse;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class IntentEvent {

    public Long userId;
    public Platform platform;
    public LuisQueryResponse luisResponse;

    public IntentEvent(Long userId, LuisQueryResponse luisResponse, Platform platform) {
        this.luisResponse = luisResponse;
        this.userId = userId;
        this.platform = platform;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public LuisQueryResponse getLuisResponse() {
        return luisResponse;
    }
}
