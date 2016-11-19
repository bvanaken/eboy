package com.eboy.redis.model;

import com.eboy.platform.Platform;

public class Subscription {

    Long userId;
    Platform platform;
    Long lastAd;
    String keywords;
    Float price;

    public Subscription(Long userId, Platform platform, Long lastAd, String keywords, Float price) {
        this.userId = userId;
        this.platform = platform;
        this.lastAd = lastAd;
        this.keywords = keywords;
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Long getLastAd() {
        return lastAd;
    }

    public String getKeywords() {
        return keywords;
    }

    public Float getPrice() {
        return price;
    }
}
