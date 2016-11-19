package com.eboy.event;

import com.eboy.platform.Platform;

public class NotifyEvent {

    Long userId;
    Platform platform;
    String url;

    public NotifyEvent(Long userId, Platform platform, String url) {
        this.userId = userId;
        this.platform = platform;
        this.url = url;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getUrl() {
        return url;
    }
}
