package com.eboy.subscriptions.model;

import com.eboy.platform.Platform;

import java.io.Serializable;
import java.util.Date;

public class Subscription implements Serializable {

    Long userId;
    Platform platform;
    Date lastAdDate;
    Long lastAd;
    String keywords;
    Float price;
    Boolean isBerlin;

    public Subscription() {
    }

    public Subscription(Long userId, Platform platform, Date lastAdDate, Long lastAd, String keywords, Float price, Boolean isBerlin) {
        this.userId = userId;
        this.platform = platform;
        this.lastAdDate = lastAdDate;
        this.keywords = keywords;
        this.price = price;
        this.lastAd = lastAd;
        this.isBerlin = isBerlin;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Date getLastAdDate() {
        return lastAdDate;
    }

    public String getKeywords() {
        return keywords;
    }

    public Float getPrice() {
        return price;
    }

    public Long getLastAd() {
        return lastAd;
    }

    public void setLastAd(Long lastAd) {
        this.lastAd = lastAd;
    }

    public void setLastAdDate(Date lastAdDate) {
        this.lastAdDate = lastAdDate;
    }

    public Boolean getIsBerlin() {
        return isBerlin;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "userId=" + userId +
                ", platform=" + platform +
                ", lastAdDate=" + lastAdDate +
                ", lastAd=" + lastAd +
                ", keywords='" + keywords + '\'' +
                ", price=" + price +
                ", isBerlin=" + isBerlin +
                '}';
    }
}
