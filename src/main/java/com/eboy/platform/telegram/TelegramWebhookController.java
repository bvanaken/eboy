package com.eboy.platform.telegram;

import com.eboy.conversation.incoming.EventProcessor;
import com.eboy.platform.Platform;
import com.eboy.platform.facebook.update.Entry;
import com.eboy.platform.facebook.update.Event;
import com.eboy.platform.facebook.update.Message;
import com.eboy.platform.facebook.update.UpdateBody;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TelegramWebhookController {

    private final static Logger logger = Logger.getLogger(TelegramWebhookController.class.getName());

    @RequestMapping(value = "/webhook", method = POST)
    public void receiveUpdate(@RequestBody Update update) throws IOException {
        logger.info("Chat id: " + update.message().chat().id());
    }
}
