package com.eboy.platform.telegram;

import com.eboy.platform.facebook.update.Event;
import com.eboy.platform.facebook.update.Message;
import com.eboy.platform.facebook.update.Postback;
import com.eboy.platform.facebook.update.QuickReply;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
public class TelegramHelper {

    private final String TESTBOT_PAGE_ID = "299663627076602";

    /**
     * Checks whether there is a message and the sender is not the bot
     *
     * @param event
     * @return true if user has sent a message
     */
    public boolean isUserMessage(Event event) {
        Assert.notNull(event);
        Assert.notNull(event.getSender());

        return !TESTBOT_PAGE_ID.equals(event.getSender().getId()) && event.getMessage() != null && event.getMessage().getQuickReply() == null;
    }

    /**
     * Checks whether the event is a successful login event
     *
     * @param event
     * @return true if event is a successful login event
     */
    public boolean isLoginEvent(Event event) {
        Assert.notNull(event);

        return event.getAccountLinking() != null && "linked".equals(event.getAccountLinking().getStatus());
    }

    /**
     * Checks whether the event is a postback event, that comes from a button click
     *
     * @param event
     * @return true if event is a postback event
     */
    public boolean isPostbackEvent(Event event) {
        Assert.notNull(event);

        Optional<String> postback = Optional.ofNullable(event.getPostback()).map(Postback::getPayload);

        return postback.isPresent();
    }

    /**
     * Checks whether the event is an answer to a quick reply
     *
     * @param event
     * @return true if event is has a quick reply payload
     */
    public boolean isQuickReplyAnswerEvent(Event event) {
        Assert.notNull(event);

        Optional<String> payload = Optional.ofNullable(event.getMessage()).map(Message::getQuickReply).map(QuickReply::getPayload);

        return payload.isPresent();
    }

}
