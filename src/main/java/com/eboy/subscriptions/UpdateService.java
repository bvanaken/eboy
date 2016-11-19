package com.eboy.subscriptions;

import com.eboy.data.EbayAdService;
import com.eboy.data.dto.Ad;
import com.eboy.event.NotifyEvent;
import com.eboy.subscriptions.model.Subscription;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
public class UpdateService {

    private final static Logger logger = Logger.getLogger(UpdateService.class.getName());

    SubscriptionPersister persister;
    EbayAdService adService;
    EventBus eventBus;

    @Autowired
    public UpdateService(SubscriptionPersister persister, EbayAdService adService, EventBus eventBus) {
        this.persister = persister;
        this.adService = adService;
        this.eventBus = eventBus;
    }

    @Scheduled(fixedDelay = 5 * 1000L)
    public void checkForUpdates() {

        Set<String> keys = persister.getKeys();

        for (String keyword : keys) {

            logger.info("keys: " + keys.toString());

            List<Subscription> subscriptions = persister.getSubscriptions(keyword);

            if (subscriptions == null || subscriptions.isEmpty()) {
                return;
            }

            Ad newestAd = adService.getLatestAd(Arrays.asList(keyword));

            logger.info("newestAd: " + newestAd);

            Date newestDate = (Date) newestAd.getDateTime().getValue();

            logger.info("newestDate: " + newestDate);
            logger.info("lastDate: " + subscriptions.get(0).getLastAd());

            // Assume all subscribers have the same last ad date
            // Check if date is before newest ad date
            if (subscriptions.get(0).getLastAd().before(newestDate)) {

                this.notifySubscribers(subscriptions, newestAd);

            }

        }

    }

    private void notifySubscribers(List<Subscription> subscriptions, Ad ad) {

        for (Subscription subscription : subscriptions) {

            eventBus.post(new NotifyEvent(subscription.getUserId(), subscription.getPlatform(), ad.getPublicUrl()));
            subscription.setLastAd((Date) ad.getDateTime().getValue());
        }

    }

}
