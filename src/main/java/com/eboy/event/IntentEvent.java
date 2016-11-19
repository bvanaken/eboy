package com.eboy.event;

import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class IntentEvent {

    public Long userId;
    public String key;
    public Platform platform;

    public IntentEvent(Long userId, String key, Platform platform) {
        this.userId = userId;
        this.key = key;
        this.platform = platform;
    }
}
