package com.eboy.subscriptions;

import com.eboy.data.EbayAdService;
import com.eboy.data.dto.Ad;
import com.eboy.event.NotifyEvent;
import com.eboy.subscriptions.model.Subscription;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
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

    @Scheduled(fixedDelay = 30 * 1000L)
    public void checkForUpdates() {

        Set<String> keys = persister.getKeys();

        for (String keyword : keys) {

            try {
                Long.valueOf(keyword);
            } catch (Exception e) {

                logger.info("keys: " + keys.toString());

                List<Subscription> subscriptions = persister.getSubscriptions(keyword);

                if (subscriptions == null) {
                    break;
                }

                // Filter berlin ones
                List<Subscription> berlinSubscriptions = subscriptions.stream().filter(Subscription::getIsBerlin).collect(Collectors.toList());
                subscriptions.removeAll(berlinSubscriptions);

                if (!subscriptions.isEmpty()) {
                    // NON BERLIN SUBSCRIBERS
                    Ad newestAd = adService.getLatestAd(Arrays.asList(keyword));
                    Long newestAdId = newestAd.getId();
                    logger.info("newestAd: " + newestAd);

                    // Assume all subscribers have the same last article
                    // Check if article is not last article
                    if (!newestAdId.equals(subscriptions.get(0).getLastAd())) {

                        this.notifySubscribers(subscriptions, newestAd);
                    }
                }

                if (berlinSubscriptions.isEmpty()) {
                    break;
                }

                // BERLIN SUBSCRIBERS
                Ad newestAdBerlin = adService.getLatestAdInBerlin(Arrays.asList(keyword));
                Long newestAdBerlinId = newestAdBerlin.getId();
                logger.info("newestAdBerlin: " + newestAdBerlin);

                // Assume all subscribers have the same last article
                // Check if article is not last article
                if (!newestAdBerlinId.equals(berlinSubscriptions.get(0).getLastAd())) {

                    this.notifySubscribers(berlinSubscriptions, newestAdBerlin);

                }

            }

        }
    }

    private void notifySubscribers(List<Subscription> subscriptions, Ad ad) {

        for (Subscription subscription : subscriptions) {

            // CHECK PRICE
            if (subscription.getPrice() >= (Double) ad.getPrice().getAmount().getValue()) {

                eventBus.post(new NotifyEvent(subscription.getUserId(), subscription.getPlatform(), ad, null));

                subscription.setLastAd(ad.getId());
                persister.persistSubscription(subscription.getKeywords(), subscription);

            } else {
                System.out.println("Sub Price: " + subscription.getPrice());
                System.out.println("Ad Price: " + (Double) ad.getPrice().getAmount().getValue());
            }
        }

    }

}
