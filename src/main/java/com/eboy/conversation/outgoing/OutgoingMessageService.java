package com.eboy.conversation.outgoing;

import com.eboy.conversation.outgoing.dto.MessageEntry;
import com.eboy.data.dto.Ad;
import com.eboy.data.dto.Price;
import com.eboy.event.ImageRecognitionEvent;
import com.eboy.event.IntentEvent;
import com.eboy.event.NotifyEvent;
import com.eboy.nlp.Intent;
import com.eboy.platform.MessageService;
import com.eboy.platform.Platform;
import com.eboy.platform.facebook.FacebookMessageService;
import com.eboy.platform.telegram.TelegramMessageService;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class OutgoingMessageService {

    private final String GENERAL_PREFIX = "general";
    private MessageService facebookMessageService;
    private MessageService telegramMessageService;
    private OutgoingMessageHelper messageHelper;

    private Map<String, List<MessageEntry>> generalMessageMap;

    private final static Logger logger = Logger.getLogger(OutgoingMessageService.class.getName());

    @Autowired
    public OutgoingMessageService(@Qualifier(FacebookMessageService.QUALIFIER) MessageService facebookMessageService, @Qualifier(TelegramMessageService.QUALIFIER) MessageService telegramMessageService, OutgoingMessageHelper messageHelper) {
        this.facebookMessageService = facebookMessageService;
        this.telegramMessageService = telegramMessageService;
        this.messageHelper = messageHelper;

        this.generalMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.GENERAL);
    }

    public void sendText(String text, String userId, Platform platform) {
        if (platform.equals(Platform.FACEBOOK)) {
            facebookMessageService.sendTextMessage(text, userId);
        } else {
            telegramMessageService.sendTextMessage(text, userId);
        }
    }


    @Subscribe
    public void handleEvent(IntentEvent event) {

        Long userId = event.getUserId();
        Intent intent = event.getIntent();

        switch (intent) {
            case getFilter:
        }
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
        String keywords = event.keywords;
        Long userId = event.userId;

        Assert.notNull(keywords);
        Assert.notNull(userId);

        logger.info("Handle image recognition.");

        this.sendText(keywords, String.valueOf(userId), event.platform);
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
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = "\n";
        sb.append("Hey! I have found a new item for you, that you subscribed for. Wanna take a look? \n");
        sb.append(NEW_LINE);
        sb.append("The price for the item is: " + amount + "\n");
        sb.append(NEW_LINE);
        sb.append("Thats all I know about this article: " + ad.getDescription().getValueAsString());
        sb.append(NEW_LINE);
        return sb.toString();
    }
}
