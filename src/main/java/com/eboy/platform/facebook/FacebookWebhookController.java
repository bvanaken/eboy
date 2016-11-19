package com.eboy.platform.facebook;

import com.eboy.conversation.incoming.EventProcessor;
import com.eboy.platform.Platform;
import com.eboy.platform.facebook.update.Entry;
import com.eboy.platform.facebook.update.Event;
import com.eboy.platform.facebook.update.Message;
import com.eboy.platform.facebook.update.UpdateBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Logger;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class FacebookWebhookController {

    private final static Logger logger = Logger.getLogger(FacebookWebhookController.class.getName());

    private final String FB_VERIFY_TOKEN = "fb-token-dsopvjsdvpi3nj349dsf999vfdfv7";

    private EventProcessor eventProcessor;
    private FacebookHelper facebookHelper;

    @Autowired
    public FacebookWebhookController(EventProcessor eventProcessor, FacebookHelper facebookHelper) {
        this.eventProcessor = eventProcessor;
        this.facebookHelper = facebookHelper;
    }

    @RequestMapping(value = "/fb/webhook", method = GET, params = "hub.mode=subscribe")
    public
    @ResponseBody
    String verifySubscription(@RequestParam("hub.challenge") String challenge,
                              @RequestParam("hub.verify_token") String verifyToken) {

        return FB_VERIFY_TOKEN.equals(verifyToken) ? challenge : null;
    }

    @RequestMapping(value = "/fb/webhook", method = POST)
    public void receiveUpdate(@RequestBody UpdateBody updateBody) throws IOException {

        for (Entry entry : updateBody.getEntries()) {
            for (Event event : entry.getEvents()) {
                String senderId = event.getSender().getId();
                Message message = event.getMessage();

                logger.info("INCOMING:\n '" + message + "'.\n");

                if (facebookHelper.isUserMessage(event)) {

                    eventProcessor.onUserMessageEvent(message.getText(), senderId, Platform.FACEBOOK);
                    return;
                }

                if (facebookHelper.isLoginEvent(event)) {

                    Long userId = Long.valueOf(event.getAccountLinking().getAuthorizationCode());

                    eventProcessor.onLoginEvent(userId, senderId, Platform.FACEBOOK);
                    return;
                }

                if (facebookHelper.isPostbackEvent(event)) {

                    String payload = event.getPostback().getPayload();

                    eventProcessor.onUserPostbackEvent(payload, senderId, Platform.FACEBOOK);
                    return;
                }

                if (facebookHelper.isQuickReplyAnswerEvent(event)) {

                    String payload = event.getMessage().getQuickReply().getPayload();

                    eventProcessor.onUserPostbackEvent(payload, senderId, Platform.FACEBOOK);
                    return;
                }
            }
        }
    }
}
