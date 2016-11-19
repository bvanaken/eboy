package com.eboy.subscriptions.model;

import com.eboy.platform.Platform;

import java.io.Serializable;
import java.util.Date;

public class Subscription implements Serializable {

    Long userId;
    Platform platform;
    Date lastAd;
    String keywords;
    Float price;

    public Subscription() {
    }

    public Subscription(Long userId, Platform platform, Date lastAd, String keywords, Float price) {
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

    public Date getLastAd() {
        return lastAd;
    }

    public String getKeywords() {
        return keywords;
    }

    public Float getPrice() {
        return price;
    }

    public void setLastAd(Date lastAd) {
        this.lastAd = lastAd;
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
