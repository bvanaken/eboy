package com.eboy.event;

import com.eboy.subscriptions.model.Subscription;

public class SubscribeEvent {

    String keywords;
    Subscription subscription;

    public SubscribeEvent(String keywords, Subscription subscription) {
        this.keywords = keywords;
        this.subscription = subscription;
    }

    public String getKeywords() {
        return keywords;
    }

    public Subscription getSubscription() {
        return subscription;
    }
}
