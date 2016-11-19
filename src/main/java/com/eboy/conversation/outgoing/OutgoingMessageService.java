package com.eboy.conversation.outgoing;

import com.eboy.conversation.outgoing.dto.Button;
import com.eboy.conversation.outgoing.dto.MessageEntry;
import com.eboy.conversation.outgoing.dto.MessagePayload;
import com.eboy.event.IntentEvent;
import com.eboy.nlp.Intent;
import com.eboy.platform.MessageService;
import com.eboy.platform.MessageType;
import com.eboy.platform.Platform;
import com.eboy.platform.facebook.FacebookMessageService;
import com.eboy.platform.telegram.TelegramMessageService;
import com.eboy.platform.telegram.TelegramWebhookController;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
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
        Assert.notNull(event.key);
        Assert.notNull(event.userId);

        logger.info("handle event: " + event.key);

        String key = event.key;
        Long userId = event.userId;

        Map<String, List<MessageEntry>> messageMap = this.generalMessageMap;

        if (messageMap != null) {

            List<MessageEntry> messageEntries = messageMap.get(key);

            if (messageEntries != null && messageEntries.size() > 0) {

                // Get random message
                Random rand = new Random();
                int index = rand.nextInt(messageEntries.size());

                MessageEntry messageEntry = messageEntries.get(index);
                String text = messageEntry.getText();

                this.sendText(text, String.valueOf(userId), event.platform);
            }
        }
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
}
