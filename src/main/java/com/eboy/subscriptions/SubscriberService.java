package com.eboy.subscriptions;

import com.eboy.subscriptions.model.SellerInfo;
import com.eboy.subscriptions.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubscriberService {

    SubscriptionPersister persister;

    @Autowired
    public SubscriberService(SubscriptionPersister persister) {
        this.persister = persister;
    }

    public SellerInfo getInfoForKeyword(String keyword) {

        List<Subscription> subscriptions = persister.getSubscriptions(keyword);

        Float minPrice = Float.MAX_VALUE;
        Float maxPrice = Float.MIN_VALUE;

        for (Subscription sub : subscriptions) {

            Float price = sub.getPrice();
            if (price < minPrice) {
                minPrice = price;
            }
            if (price > maxPrice) {
                maxPrice = price;
            }
        }

        if (minPrice.equals(Float.MAX_VALUE)) {
            minPrice = 0f;
        }
        return new SellerInfo(subscriptions.size(), minPrice, maxPrice);

    }
}
