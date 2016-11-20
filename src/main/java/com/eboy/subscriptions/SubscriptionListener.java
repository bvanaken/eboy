package com.eboy.subscriptions;

import com.eboy.event.SubscribeEvent;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionListener {

    @Autowired
    SubscriptionPersister persister;

    @Subscribe
    public void onSubscribeEvent(SubscribeEvent event) {

        persister.persistSubscription(event.getKeywords(), event.getSubscription());

    }

}
