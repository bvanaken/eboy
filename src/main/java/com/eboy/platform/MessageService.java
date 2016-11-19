package com.eboy.platform;

import com.eboy.nlp.Intent;

import java.util.HashMap;
import java.util.List;

public interface MessageService {

    void sendTextMessage(String text, String userId);

    void sendAttachment(String url, MessageType type, String userId);

    void sendLoginTemplate(String text, String image, String userId);

    void sendQuickReplies(String text, HashMap<String, Intent> repliesWithPayload, String userId);

    void sendGenericTemplate(final List<String> titles, final List<String> subTitles, final List<String> imageUrls, final List<List<com.eboy.conversation.outgoing.dto.Button>> buttonLists, final String userId);

}
