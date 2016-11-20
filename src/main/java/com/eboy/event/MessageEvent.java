package com.eboy.event;

import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class MessageEvent {

    public Long userId;
    public Platform platform;
    public String text;

    public MessageEvent(Long userId, String text, Platform platform) {
        this.text = text;
        this.userId = userId;
        this.platform = platform;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getText() {
        return text;
    }
}
