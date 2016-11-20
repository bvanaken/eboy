package com.eboy.subscriptions;

import com.eboy.data.EbayAdService;
import com.eboy.data.dto.Ad;
import com.eboy.event.NotifyEvent;
import com.eboy.subscriptions.model.Subscription;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

            String dateStr = (String) newestAd.getDateTime().getValue();
            Date newestDate = this.dateFromString(dateStr);

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
            Date adDate = dateFromString((String) ad.getDateTime().getValue());

            logger.info("keys are: " + subscription.getKeywords());

            subscription.setLastAd(adDate);
            persister.persistSubscription(subscription.getKeywords(), subscription);
        }

    }

    private Date dateFromString(String dateString) {

        if (dateString.length() == 28) {
            dateString = dateString.substring(0, dateString.length() - 9);
        }

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        try {
            return dateFormatter.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

}
