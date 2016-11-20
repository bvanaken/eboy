package com.eboy.conversation.outgoing;

import com.eboy.conversation.incoming.SearchQuery;
import com.eboy.conversation.outgoing.dto.MessageEntry;
import com.eboy.data.EbayAdService;
import com.eboy.data.dto.Ad;
import com.eboy.data.dto.Price;
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
import com.eboy.subscriptions.SubscriptionPersister;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.NumberFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class OutgoingMessageService {

    private final static Logger logger = Logger.getLogger(OutgoingMessageService.class.getName());
    private final String GENERAL_PREFIX = "general";
    private MessageService facebookMessageService;
    private MessageService telegramMessageService;
    private OutgoingMessageHelper messageHelper;
    private SubscriptionPersister persister;
    @Autowired
    private EbayAdService adService;
    @Autowired
    private EventBus eventBus;
    private Map<String, List<MessageEntry>> generalMessageMap;

    @Autowired
    public OutgoingMessageService(@Qualifier(FacebookMessageService.QUALIFIER) MessageService facebookMessageService, @Qualifier(TelegramMessageService.QUALIFIER) MessageService telegramMessageService, OutgoingMessageHelper messageHelper,
                                  SubscriptionPersister persister) {
        this.facebookMessageService = facebookMessageService;
        this.telegramMessageService = telegramMessageService;
        this.messageHelper = messageHelper;
        this.persister = persister;

        this.generalMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.GENERAL);
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

        new SearchQuery(null, null, keyword, null);

        if (ads == null || ads.size() < 2){


        }

        if (ads != null && ads.size() > 1) {
            eventBus.post(new SpecifyEvent());
            this.sendText("I found " + ads.size() + " Articles for you! Do you want to specify a bit more?", String.valueOf(userId), platform);

        }
    }

    @Subscribe
    public void handleEvent(IntentEvent event) {

        Long userId = event.getUserId();
        LuisQueryResponse response = event.getLuisResponse();

        Intent intent = response.getTopIntent().getIntent();
        List<LuisEntity> entities = response.getEntities();

        switch (intent) {
            case getItem:

                Optional<LuisEntity> itemType = entities.stream().filter(v -> v.getType().equals("itemType")).findFirst();

                String keyword = itemType.isPresent() ? itemType.get().getEntity() : null;

                if (keyword != null) {
                    onKeywordDetected(keyword, userId, event.getPlatform());
                }
                break;

            case getFilter:
                ArrayList<String> keywords = new ArrayList<>();
                for (LuisEntity entity : entities) {
                    keywords.add(entity.getEntity());
                }

                SearchQuery q = new SearchQuery();

                break;
            case getLocation:
                Optional<LuisEntity> locationType = entities.stream().filter(v -> v.getType().equals("locationType")).findFirst();
                String locType = locationType.get().getType();
                if (locType.equals("locationType::districtOfBerlin")) {

                }
                break;
        }
    }

    @Subscribe
    public void handleEvent(SpecifyEvent event) {


    }

    @Subscribe
    public void handleEvent(NotifyEvent event) {
        Ad data = event.data;
        Long userId = event.userId;

        Assert.notNull(data);
        Assert.notNull(userId);

        logger.info("Handle latest Ad.");

        String lastAdMessage = lastAdMessage(data);
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

        Tag[] categories = recognition.tags;

        Tag tag = categories[categories.length - 1];

        this.sendText(tag.name, String.valueOf(userId), event.platform);
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

    private String lastAdMessage(Ad ad) {
        Price price = ad.getPrice();
        Double amount = (Double) price.getAmount().getValue();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(amount);

        StringBuilder sb = new StringBuilder();
        String NEW_LINE = "\n";



        sb.append("Hey! I have found a new item for you, that you subscribed for. Wanna take a look? \n");
        sb.append(NEW_LINE);
        sb.append("The price for the item is: " + moneyString + "€ \n");
        sb.append(NEW_LINE);
        sb.append("Thats all I know about this article: " + ad.getDescription().getValueAsString());
        sb.append(NEW_LINE);
        return sb.toString();
    }

    @Subscribe
    public void handleEvent(StartEvent event) {
        sendText("Hey, may I help you? Do you want to buy or do you want to sell something?",String.valueOf(event.getUserId()), event.platform);
    }

    @Subscribe
    public void handleEvent(NoClueEvent event) {
        /*sendText("I have no clue what you want from me.",String.valueOf(event.getUserId()), event.platform);*/
        sendText("http://s2.quickmeme.com/img/29/2964505b376c9cee5bd5d440d750fbd81448ed7907086a27334639ff0f009466.jpg",String.valueOf(event.getUserId()), event.platform);
    }
}
