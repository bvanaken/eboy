package com.eboy.event;

import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class NoClueEvent {

    public Long userId;
    public Platform platform;

    public NoClueEvent(Long userId, Platform platform) {
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
