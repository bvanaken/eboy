package com.eboy.subscriptions;

import org.springframework.beans.factory.annotation.Autowired;

public class SubscriberService {

    SubscriptionPersister persister;

    @Autowired
    public SubscriberService(SubscriptionPersister persister) {
        this.persister = persister;
    }

}
