package com.eboy.redis.model;

import com.eboy.platform.Platform;

import java.io.Serializable;

public class Subscription implements Serializable {

    Long userId;
    Platform platform;
    Long lastAd;
    String keywords;
    Float price;

    public Subscription() {
    }

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

    @Override
    public String toString() {
        return "Subscription{" +
                "userId=" + userId +
                ", platform=" + platform +
                ", lastAd=" + lastAd +
                ", keywords='" + keywords + '\'' +
                ", price=" + price +
                '}';
    }
}
