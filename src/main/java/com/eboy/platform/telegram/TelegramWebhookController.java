package com.eboy.platform.telegram;

import com.eboy.data.EbayAdService;
import com.eboy.data.dto.Ad;
import com.eboy.event.NotifyEvent;
import com.eboy.nlp.Intent;
import com.eboy.nlp.luis.LuisProcessor;
import com.eboy.platform.Platform;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class TelegramWebhookController {

    @Autowired
    private EventBus eventBus;

    @Autowired
    private LuisProcessor luisProcessor;

    @Autowired
    private EbayAdService ebayAdService;

    private final static Logger logger = Logger.getLogger(TelegramWebhookController.class.getName());

    @RequestMapping(value = "/telegram/webhook", method = POST)
    public void receiveUpdate(@RequestBody com.eboy.platform.telegram.model.Update update) throws IOException {
        logger.info("Chat id: " + update.toString());

        Long chatId = update.getMessage().getChat().getId();
        String text = update.getMessage().getText();

        Intent intent = luisProcessor.getIntentFromText(update.getMessage().getText());

        List<String> keywords = new ArrayList<String>();
        keywords.add(text);

        List<Ad> adsForKeywords = ebayAdService.getAdsForKeywords(keywords);

        Ad latestAd = adsForKeywords.get(0);

        if(latestAd==null) {
            return;
        }

        NotifyEvent event = new NotifyEvent(chatId, latestAd, Platform.TELEGRAM);
        eventBus.post(event);
    }
}
