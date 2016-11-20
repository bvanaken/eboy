package com.eboy.conversation.outgoing;

import com.eboy.conversation.incoming.SearchQuery;
import com.eboy.conversation.outgoing.dto.MessageEntry;
import com.eboy.data.EbayAdService;
import com.eboy.data.ExtendedAd;
import com.eboy.data.MsAnalyticService.MsTextAnalyticService;
import com.eboy.data.dto.Ad;
import com.eboy.data.keyPhraseModel.KeyPhraseModel;
import com.eboy.event.*;
import com.eboy.mv.model.Recognition;
import com.eboy.mv.model.Tag;
import com.eboy.nlp.Intent;
import com.eboy.nlp.luis.model.LuisEntity;
import com.eboy.nlp.luis.model.LuisQueryResponse;
import com.eboy.platform.MessageService;
import com.eboy.platform.Platform;
import com.eboy.platform.facebook.FacebookMessageService;
import com.eboy.platform.telegram.TelegramMessageService;
import com.eboy.subscriptions.QueryPersister;
import com.eboy.subscriptions.SubscriberService;
import com.eboy.subscriptions.SubscriptionPersister;
import com.eboy.subscriptions.model.SellerInfo;
import com.eboy.subscriptions.model.Subscription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OutgoingMessageService {

    private final static Logger logger = Logger.getLogger(OutgoingMessageService.class.getName());
    private final String GENERAL_PREFIX = "general";

    @Autowired
    MsTextAnalyticService textAnalyser;

    private MessageService facebookMessageService;
    private MessageService telegramMessageService;
    private OutgoingMessageHelper messageHelper;
    private SubscriptionPersister persister;

    @Autowired
    private QueryPersister queryPersister;
    @Autowired
    private SubscriberService subscriberService;
    @Autowired
    private EbayAdService adService;
    @Autowired
    private EventBus eventBus;
    private Map<String, List<MessageEntry>> generalMessageMap;
    private List<Tag> tagsToKick = new ArrayList<>();

    @Autowired
    public OutgoingMessageService(@Qualifier(FacebookMessageService.QUALIFIER) MessageService facebookMessageService, @Qualifier(TelegramMessageService.QUALIFIER) MessageService telegramMessageService, OutgoingMessageHelper messageHelper,
                                  SubscriptionPersister persister) {
        this.facebookMessageService = facebookMessageService;
        this.telegramMessageService = telegramMessageService;
        this.messageHelper = messageHelper;
        this.persister = persister;

        this.generalMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.GENERAL);

        tagsToKick.add(new Tag("ground"));
        tagsToKick.add(new Tag("floor"));
        tagsToKick.add(new Tag("indoor"));
        tagsToKick.add(new Tag("furniture"));
        tagsToKick.add(new Tag("wall"));
    }

    public void sendText(String text, String userId, Platform platform) {
        if (platform.equals(Platform.FACEBOOK)) {
            facebookMessageService.sendTextMessage(text, userId);
        } else {
            telegramMessageService.sendTextMessage(text, userId);
        }
    }

    public void onKeywordDetected(String keyword, Long userId, Platform platform) {

        List<Ad> ads = adService.getAdsForKeywords(Arrays.asList(keyword));

        SearchQuery searchQuery = new SearchQuery(null, null, keyword);

        if (ads == null || ads.isEmpty()) {
            eventBus.post(new AskUpdateEvent(userId, searchQuery, platform));
        }

        if (ads != null && ads.size() > 1) {
            this.sendText("I found " + adService.getNumberOfAdsForKeywords(Arrays.asList(keyword)) + " articles for you! Do you want to specify a bit more?", String.valueOf(userId), platform);
            queryPersister.persistSearchQuery(userId, searchQuery);

        } else {

            eventBus.post(new NotifyEvent(userId, platform, ads.get(0), searchQuery));
        }
    }

    public void onQueryExtended(SearchQuery searchQuery, String extraKeywords, Long userId, Platform platform) {

        if (searchQuery.getMainKeyword() == null) {
            this.sendText("A great choice, but what exactly do you wanna buy? You can tell how much you want to pay or where you live for example.", String.valueOf(userId), platform);
        }

        if (searchQuery.getMaxPrice() == null) {
            this.sendText(extraKeywords + ", cool. Any price limit from your side?", String.valueOf(userId), platform);
            return;
        }
        if (searchQuery.getBerlin() == null) {
            this.sendText("Sounds good. Do you want to search in a specific city?", String.valueOf(userId), platform);

        } else {

            this.sendText("I got it! Let's see what is out there...", String.valueOf(userId), platform);

            List<Ad> ads = adService.getAdsForKeywords(Arrays.asList(searchQuery.getMainKeyword()), searchQuery.getMaxPrice(), searchQuery.getBerlin());

            eventBus.post(new NotifyEvent(userId, platform, ads.get(0), searchQuery));

        }
    }

    @Subscribe
    public void handleEvent(IntentEvent event) {

        Long userId = event.getUserId();
        LuisQueryResponse response = event.getLuisResponse();

        Intent intent = response.getTopIntent().getIntent();
        List<LuisEntity> entities = response.getEntities();

        SearchQuery query = queryPersister.getSearchQuery(userId);
        if (query == null) {
            query = new SearchQuery(null, null, null);
        }

        switch (intent) {
            case yes:
                if (query.isComplete()) {
                    this.sendText("Great, you will get a notification, once there's a new item.", String.valueOf(userId), event.getPlatform());

                    Subscription subscription = new Subscription(userId, event.getPlatform(), null, null, query.getMainKeyword(), query.getMaxPrice(), query.getBerlin());
                    persister.persistSubscription(String.valueOf(userId), subscription);
                }
                break;

            case no:
                if (query.isComplete()) {
                    this.sendText("Alright, just let me know, when you're up for something else.", String.valueOf(userId), event.getPlatform());
                }
                break;

            case triggerSubscribe:

                this.sendText("Do you want me to notify you once a new " + query.getMainKeyword() + " with this features is available?", String.valueOf(userId), event.getPlatform());
                break;

            case getItem:

                Optional<LuisEntity> itemType = entities.stream().filter(v -> v.getType().equals("itemType")).findFirst();

                String keyword = itemType.isPresent() ? itemType.get().getEntity() : null;

                if (keyword != null) {
                    this.onKeywordDetected(keyword, userId, event.getPlatform());
                }
                break;

            case getFilter:
                String keywords = "";
                for (LuisEntity entity : entities) {
                    keywords += entity.getEntity() + " ";
                }
                if (keywords.length() > 0) {
                    keywords = keywords.substring(0, keywords.length() - 1);
                }

                query.setMainKeyword(query.getMainKeyword() + " " + keywords);
                queryPersister.persistSearchQuery(userId, query);

                this.onQueryExtended(query, keywords, userId, event.getPlatform());

                break;
            case getLocation:
                Optional<LuisEntity> locationType = entities.stream().filter(v -> v.getType().equals("locationType")).findFirst();
                String locEntity = locationType.isPresent() ? locationType.get().getEntity().toLowerCase() : null;
                String locType = locationType.isPresent() ? locationType.get().getType() : null;

                if (("locationType::districtOfBerlin").equals(locType) || ("berlin").equals(locEntity)) {

                    query.setBerlin(true);
                } else {
                    query.setBerlin(false);
                }
                queryPersister.persistSearchQuery(userId, query);

                onQueryExtended(query, locEntity, userId, event.getPlatform());

                break;
            case getPriceRange:

                String number = findNumber(response.getQuery());

                query.setMaxPrice(Float.valueOf(number));
                queryPersister.persistSearchQuery(userId, query);

                onQueryExtended(query, "", userId, event.getPlatform());
                break;
        }
    }

    @Subscribe
    public void handleEvent(NotifyEvent event) throws IOException {
        Ad data = event.getData();

        ObjectMapper mapper = new ObjectMapper();

        List<Ad> ads = new ArrayList<>();
        ads.add(data);

        String json = this.textAnalyser.analyzeAds(ads);
        KeyPhraseModel keyPhrases = mapper.readValue(json, KeyPhraseModel.class);

        ExtendedAd ad = new ExtendedAd(data, keyPhrases);
        Long userId = event.userId;

        Assert.notNull(data);
        Assert.notNull(userId);

        logger.info("Handle latest Ad.");

        String lastAdMessage = lastAdMessage(ad);
        this.sendText(lastAdMessage, String.valueOf(userId), event.platform);
    }

    @Subscribe
    public void handleEvent(ImageRecognitionEvent event) {
        Recognition recognition = event.recognition;
        Long userId = event.userId;

        Assert.notNull(recognition);
        Assert.notEmpty(recognition.tags);
        Assert.notNull(userId);

        logger.info("Handle image recognition.");

        // instantiate QueryObject

        List<Tag> categories = recognition.tags;

        categories.removeAll(tagsToKick);

        if (categories.isEmpty()) {
            this.sendText("Sorry, I couldn't figure out a valuable product for your request. Maybe you should just write me, what you are looking for!", String.valueOf(userId), event.platform);
            return;
        }

        Tag tag = categories.get(0);

        onKeywordDetected(tag.name, userId, event.platform);
    }

    private String getMessageForKey(final String key, final String[] params) {
        Assert.notNull(key);

        Map<String, List<MessageEntry>> messageMap = this.generalMessageMap;

        if (messageMap != null) {

            List<MessageEntry> messageEntries = messageMap.get(key);

            if (messageEntries != null && messageEntries.size() > 0) {

                // Get random message
                Random rand = new Random();
                int index = rand.nextInt(messageEntries.size());

                MessageEntry messageEntry = messageEntries.get(index);

                // insert emoticons and params
                return messageHelper.fillInPatterns(messageEntry.getText(), params);
            }
        }
        return null;
    }

    private String lastAdMessage(ExtendedAd ad) {
        Double amount = (Double) ad.getPrice();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(amount);

        StringBuilder sb = new StringBuilder();
        String NEW_LINE = "\n";

        sb.append("Hey! I have found a new item for you, that you subscribed for. Wanna take a look? \n");
        sb.append(NEW_LINE);
        sb.append("The price for the item is: " + moneyString + "€ \n");
        sb.append(NEW_LINE);
        //sb.append("Thats all I know about this article: " + ad.getDescription().getValueAsString());
        sb.append("I think it is noteworthy because:" + this.getRandomKeyPhrase(ad));
        sb.append(NEW_LINE);
        return sb.toString();
    }

    @Subscribe
    public void handleEvent(StartEvent event) {
        sendText("Hey, may I help you? Do you want to buy or do you want to sell something?", String.valueOf(event.getUserId()), event.platform);
    }


    @Subscribe
    public void handleEvent(SellEvent event) {
        SellerInfo info = subscriberService.getInfoForKeyword(event.getKeywords());
        String userId = String.valueOf(event.getUserId());
        Platform platform = event.getPlatform();

        if(info != null){

            Integer subscribers = info.getNumberSubscribers();

            Float priceMin = info.getPriceMin();
            Float priceMax = info.getPriceMax();

            if (subscribers < 0) {
                sendText("Bummer, there's no one directly subscribed to your article at the moment. Maybe you should wait a bit or just try your luck on the market!", userId, platform);

            } else if (subscribers < 5) {
                sendText("There are currently " + subscribers + " looking for your article. The price range is between " + priceMin + " € and " + priceMax + " €.", userId, platform);

            } else {
                sendText("I think that's a great idea! There are currently " + subscribers + " looking for your article. Just the right time to sell! The price range lies between " + priceMin + " € and " + priceMax + " €.", userId, platform);
            }
        } else {
            sendText("Bummer, there's no one directly subscribed to your article at the moment. Maybe you should wait a bit or just try your luck on the market!", userId, platform);
        }

    }

    public String findNumber(String text) {
        Pattern p = Pattern.compile("[\\d,]+");

        Matcher m = p.matcher(text);

        if (m.find()) {
            return m.group();
        }
        return null;
    }

    @Subscribe
    public void handleEvent(NoClueEvent event) {
        /*sendText("I have no clue what you want from me.",String.valueOf(event.getUserId()), event.platform);*/
        sendText("http://s2.quickmeme.com/img/29/2964505b376c9cee5bd5d440d750fbd81448ed7907086a27334639ff0f009466.jpg", String.valueOf(event.getUserId()), event.platform);
    }

    private String getRandomKeyPhrase(ExtendedAd ad) {

        String[] positives = new String[]{
                "neu", "neuwertig", "unbenutzt", "hochwertig", "wertig", "modern",
                "günstig", "schnell", "leistung", "hochgradig", "luxuriös", "ideal", "praktisch", "gut", "beste", "sauber"};

        String[] keyphrases = ad.getKeyPhrases();
        for (int i = 0; i < positives.length; i++) {
            for (int j = 0; j <keyphrases.length; j++) {
                if (positives[i].contains(keyphrases[j].toLowerCase())){
                    return keyphrases[j];
                };
            }
        }

        int index = (int) Math.round(Math.random() * keyphrases.length);
        return ad.getKeyPhrases()[index];
    }
}
