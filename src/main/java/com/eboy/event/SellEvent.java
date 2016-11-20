package com.eboy.event;

import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class SellEvent {

    public Long userId;
    public Platform platform;
    private String keywords;

    public SellEvent(Long userId, Platform platform, String keywords) {
        this.userId = userId;
        this.platform = platform;
        this.keywords = keywords;
    }

    public Long getUserId() {
        return userId;
    }

    public String getKeywords() {
        return keywords;
    }

    public Platform getPlatform() {
        return platform;
    }

}
