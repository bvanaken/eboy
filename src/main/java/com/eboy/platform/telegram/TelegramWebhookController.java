package com.eboy.platform.telegram;

import com.eboy.conversation.incoming.EventProcessor;
import com.eboy.event.IntentEvent;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TelegramWebhookController {

    @Autowired
    private EventProcessor eventProcessor;

    @Autowired
    private EventBus eventBus;

    private final static Logger logger = Logger.getLogger(TelegramWebhookController.class.getName());

    @RequestMapping(value = "/telegram/webhook", method = POST)
    public void receiveUpdate(@RequestBody com.eboy.platform.telegram.model.Update update) throws IOException {
        logger.info("Chat id: " + update.toString());
        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChat().getId();
        eventBus.post(new IntentEvent(chatId, text));
    }
}
