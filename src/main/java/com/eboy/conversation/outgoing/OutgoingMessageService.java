package com.eboy.conversation.outgoing;

import com.eboy.conversation.outgoing.dto.Button;
import com.eboy.conversation.outgoing.dto.MessageEntry;
import com.eboy.conversation.outgoing.dto.MessagePayload;
import com.eboy.nlp.Intent;
import com.eboy.platform.MessageService;
import com.eboy.platform.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutgoingMessageService {

    @Value("${image.url}")
    private String IMAGE_BASE_URL;

    private final String GENERAL_PREFIX = "general";
    private final String ONBOARDING_PREFIX = "start";
    private final String ANIMALS_PREFIX = "animals";
    private final String MARKET_PREFIX = "market";
    private final String FAQ_PREFIX = "faq";

    private MessageService messageService;
    private OutgoingMessageHelper messageHelper;

    private Map<String, List<MessageEntry>> generalMessageMap;
    private Map<String, List<MessageEntry>> startMessageMap;
    private Map<String, List<MessageEntry>> animalsMessageMap;
    private Map<String, List<MessageEntry>> marketMessageMap;
    private Map<String, List<MessageEntry>> faqMessageMap;

    @Autowired
    public OutgoingMessageService(MessageService messageService, OutgoingMessageHelper messageHelper) {
        this.messageService = messageService;
        this.messageHelper = messageHelper;

        this.generalMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.GENERAL);
        this.startMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.ONBOARDING);
        this.animalsMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.ANIMALS);
        this.marketMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.MARKET);
        this.faqMessageMap = this.messageHelper.loadMessageMap(OutgoingMessageHelper.Domain.FAQ);
    }

    public void sendText(final String text, final String userId) {
        messageService.sendTextMessage(text, userId);
    }

    public void sendImage(String image, final String userId) {
        if (messageHelper.isRelativeUrl(image)) {
            image = IMAGE_BASE_URL + image;
        }

        messageService.sendAttachment(image, MessageType.IMAGE, userId);
    }

    public void sendLoginRequest(final String text, String image, final String userId) {
        if (messageHelper.isRelativeUrl(image)) {
            image = IMAGE_BASE_URL + image;
        }

        // Check if text is key for MessageMaps
        Optional<String> textFromKey = Optional.ofNullable(this.getMessageForKey(text, null));

        messageService.sendLoginTemplate(textFromKey.orElse(text), image, userId);
    }

    public void sendImageWithText(final String text, final String image, final String userId) {
        this.sendText(text, userId);
        this.sendImage(image, userId);
    }

    public void sendQuickReplies(final String text, final Object[] replyArray, final String userId) {
        Assert.notNull(replyArray);
        Assert.notNull(userId);
        Assert.isTrue(replyArray.length % 2 == 0, "Odd number in quick replies array not allowed.");

        HashMap<String, Intent> quickReplies = new HashMap<>();

        // Put Reply-Array into Hashmap
        for (int i = 0; i <= replyArray.length / 2; i = i + 2) {
            quickReplies.put((String) replyArray[i], (Intent) replyArray[i + 1]);
        }

        // Check if text is key for MessageMaps
        Optional<String> textFromKey = Optional.ofNullable(this.getMessageForKey(text, null));

        messageService.sendQuickReplies(textFromKey.orElse(text), quickReplies, userId);
    }

    public void sendGalleryWithImageAndButtons(final List<String> titles, final List<String> subTitles, List<String> imageUrls, final List<List<Button>> buttons, final String userId) {
        Assert.notNull(titles);
        Assert.notNull(subTitles);
        Assert.notNull(imageUrls);
        Assert.notNull(buttons);
        Assert.notNull(userId);
        Assert.isTrue(titles.size() == subTitles.size(), "Element lists size must be the same");
        Assert.isTrue(titles.size() == imageUrls.size(), "Element lists size must be the same");
        Assert.isTrue(titles.size() == buttons.size(), "Element lists size must be the same");

        // Check for relative imageUrls (assuming they are all of the same kind)
        if (imageUrls.size() > 0) {
            if (messageHelper.isRelativeUrl(imageUrls.get(0))) {
                imageUrls = imageUrls.stream().map(url -> IMAGE_BASE_URL + url).collect(Collectors.toList());
            }
        }

        messageService.sendGenericTemplate(titles, subTitles, imageUrls, buttons, userId);
    }

    public void sendMessageForKey(final String key, final String userId, final String[] params) {
        Assert.notNull(key);
        Assert.notNull(userId);

        Map<String, List<MessageEntry>> messageMap = this.messageMapForKey(key);

        if (messageMap != null) {

            List<MessageEntry> messageEntries = messageMap.get(key);

            if (messageEntries != null && messageEntries.size() > 0) {

                // Get random message
                Random rand = new Random();
                int index = rand.nextInt(messageEntries.size());

                MessageEntry messageEntry = messageEntries.get(index);

                MessagePayload payload = messageEntry.getPayload();
                String text = messageHelper.fillInPatterns(messageEntry.getText(), params);

                if (payload != null && payload.getUrl() != null) {

                    switch (payload.getType()) {
                        case IMAGE:
                            this.sendImageWithText(text, payload.getUrl(), userId);
                            break;
                    }

                } else {

                    this.sendText(text, userId);
                }
            }
        }
    }

    public void sendMessageForKey(final String key, final String userId) {
        this.sendMessageForKey(key, userId, null);
    }

    private Map<String, List<MessageEntry>> messageMapForKey(final String key) {
        try {
            String domain = key.split("_")[0];

            switch (domain) {
                case GENERAL_PREFIX:
                    return generalMessageMap;
                case ONBOARDING_PREFIX:
                    return startMessageMap;
                case ANIMALS_PREFIX:
                    return animalsMessageMap;
                case MARKET_PREFIX:
                    return marketMessageMap;
                case FAQ_PREFIX:
                    return faqMessageMap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getMessageForKey(final String key, final String[] params) {
        Assert.notNull(key);

        Map<String, List<MessageEntry>> messageMap = this.messageMapForKey(key);

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
