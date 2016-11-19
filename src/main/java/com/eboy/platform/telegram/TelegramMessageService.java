package com.eboy.platform.telegram;

import com.eboy.conversation.outgoing.dto.ButtonType;
import com.eboy.nlp.Intent;
import com.eboy.platform.MessageService;
import com.eboy.platform.MessageType;
import com.eboy.platform.facebook.message.*;
import com.eboy.platform.facebook.message.SendResponse;
import com.eboy.platform.facebook.update.Member;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

@Service
public class TelegramMessageService implements MessageService {

    private final static Logger logger = Logger.getLogger(TelegramMessageService.class.getName());

    private final String TOKEN = "276165906:AAFyFqmodHM-ji8nhNu0XtYBrxbT57iGcu0";

    public static void main(String[] args) {
        TelegramMessageService t = new TelegramMessageService();
        t.sendTextMessage("Hey bot", "190957563");
    }

    @Override
    public void sendTextMessage(final String text, final String userId) {
        TelegramBot bot = TelegramBotAdapter.build(TOKEN);

        SendMessage message = new SendMessage(text, userId).parseMode(ParseMode.Markdown);

        bot.execute(message, new Callback<SendMessage, com.pengrad.telegrambot.response.SendResponse>() {
            @Override
            public void onResponse(SendMessage request, com.pengrad.telegrambot.response.SendResponse response) {

            }

            @Override
            public void onFailure(SendMessage request, IOException e) {

            }
        });
    }

    @Override
    public void sendQuickReplies(final String text, final HashMap<String, Intent> repliesWithPayload, final String userId) {

    }

    @Override
    public void sendAttachment(final String url, final MessageType type, final String userId) {

    }

    @Override
    public void sendLoginTemplate(final String text, final String image, final String userId) {

    }

    @Override
    public void sendGenericTemplate(final List<String> titles, final List<String> subTitles, final List<String> imageUrls, final List<List<com.eboy.conversation.outgoing.dto.Button>> buttonLists, final String userId) {
    }

}
