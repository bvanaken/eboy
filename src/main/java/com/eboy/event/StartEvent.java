package com.eboy.event;

import com.eboy.nlp.luis.model.LuisQueryResponse;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class StartEvent {

    public Long userId;
    public Platform platform;

    public StartEvent(Long userId, Platform platform) {
        this.userId = userId;
        this.platform = platform;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

}
