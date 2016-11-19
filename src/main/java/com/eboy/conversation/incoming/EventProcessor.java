package com.eboy.conversation.incoming;

import com.eboy.conversation.outgoing.OutgoingMessageService;
import com.eboy.nlp.Intent;
import com.eboy.platform.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EventProcessor {

    private final static Logger logger = Logger.getLogger(EventProcessor.class.getName());

    private OutgoingMessageService messageService;
    private MessageProcessor messageProcessor;

    @Autowired
    public EventProcessor(OutgoingMessageService messageService, MessageProcessor messageProcessor) {
        this.messageService = messageService;
        this.messageProcessor = messageProcessor;

    }

    public void onStartEvent(final String senderId, final Platform platform) {


    }

    public void onLoginEvent(final Long appUserId, final String senderId, final Platform platform) {


    }

    public void onUserMessageEvent(final String text, final String senderId, final Platform platform) {


    }

    public void onUserPostbackEvent(String payload, String senderId, Platform platform) {

        try {
            Intent intent = Intent.valueOf(payload);
            this.onIntentDetected(intent, senderId, platform);

        } catch (IllegalArgumentException e) {
            logger.warning("Payload '" + payload + "' can't be matched to an intent");
        }
    }

    public void onIntentDetected(Intent intent, String senderId, Platform platform) {

    }

    public void onIntentNotDetected(String senderId, Platform platform) {

        messageService.sendMessageForKey("general_did_not_understand", senderId);

    }

    private void handleConfirmation(final String senderId, final Platform platform) {

    }

    private void handleRefusal(final String senderId, final Platform platform) {

    }
}
