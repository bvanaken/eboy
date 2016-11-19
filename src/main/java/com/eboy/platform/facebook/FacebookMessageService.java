package com.eboy.platform.facebook;

import com.eboy.conversation.outgoing.dto.ButtonType;
import com.eboy.nlp.Intent;
import com.eboy.platform.MessageService;
import com.eboy.platform.MessageType;
import com.eboy.platform.facebook.message.*;
import com.eboy.platform.facebook.update.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Logger;

@Service
@Qualifier(value = FacebookMessageService.QUALIFIER)
public class FacebookMessageService implements MessageService {

    public static final String QUALIFIER = "FacebookMessageService";

    private final static Logger logger = Logger.getLogger(FacebookMessageService.class.getName());

    private final String PAGE_ACCESS_TOKEN = "EAAPfk4CcikoBAOj4pffNbiBQBjq9WAQq9y1kFEnp1RsmsqPrpJWS8AdQhqvXFk57RhiWOgp9BRRyJnmW85CZCozcvh8P2sM8914OhtGqc8nsB1WiF2J2jrqBcd887ApiHhrcLhyFN24rLrKVjJR3phcpfmDFcWnUC1pGpVgZDZD";
    private final String FB_MESSAGE_URL = "https://graph.facebook.com/v2.6/me/messages?access_token=" + PAGE_ACCESS_TOKEN;
    private final String LOGIN_FORM_URL = "https://422da322.ngrok.io/login_form.html";

    RestTemplate restTemplate;

    @Autowired
    public FacebookMessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void sendTextMessage(final String text, final String userId) {

        BaseMessage message = TextMessage.Builder.create()
                .withText(text)
                .build();

        this.sendToUser(message, userId);
    }

    @Override
    public void sendQuickReplies(final String text, final HashMap<String, Intent> repliesWithPayload, final String userId) {

        List<QuickReplyElement> quickReplies = new ArrayList<>();

        for (String quickReplyTitle : repliesWithPayload.keySet()) {

            Intent payload = repliesWithPayload.get(quickReplyTitle);

            QuickReplyElement reply = QuickReplyElement.Builder.create()
                    .withTitle(quickReplyTitle)
                    .withPayload(payload.name())
                    .build();

            quickReplies.add(reply);
        }

        BaseMessage message = TextMessage.Builder.create()
                .withText(text)
                .withQuickReplies(quickReplies)
                .build();

        this.sendToUser(message, userId);

    }

    @Override
    public void sendAttachment(final String url, final MessageType type, final String userId) {

        // Sending message will take time, so indicate typing first
        this.sendTypingOn(userId);

        BaseMessage message = AttachmentMessage.Builder.create()
                .withAttachment(
                        Attachment.Builder.create()
                                .withPayload(
                                        AttachmentPayload.Builder.create()
                                                .withUrl(url)
                                                .build())
                                .withType(type)
                                .build())
                .build();

        this.sendToUser(message, userId);
    }

    @Override
    public void sendLoginTemplate(final String text, final String image, final String userId) {

        // Sending message will take time, so indicate typing first
        this.sendTypingOn(userId);

        BaseMessage message = AttachmentMessage.Builder.create()
                .withAttachment(Attachment.Builder.create()
                        .withPayload(
                                TemplatePayload.Builder.create()
                                        .withElements(Collections.singletonList(

                                                PayloadElement.Builder.create()
                                                        .withTitle(text)
                                                        .withImageUrl(image)
                                                        .withButtons(Arrays.asList(

                                                                Button.Builder.create()
                                                                        .ofType(ButtonType.LOGIN)
                                                                        .withUrl(LOGIN_FORM_URL)
                                                                        .build(),

                                                                Button.Builder.create()
                                                                        .ofType(ButtonType.INTENT)
                                                                        .withTitle("No, thanks")
                                                                        .withPayload("REFUSE")
                                                                        .build()
                                                        )).build()
                                        ))
                                        .build()
                        )
                        .withType(MessageType.TEMPLATE)
                        .build())
                .build();

        this.sendToUser(message, userId);
    }

    @Override
    public void sendGenericTemplate(final List<String> titles, final List<String> subTitles, final List<String> imageUrls, final List<List<com.eboy.conversation.outgoing.dto.Button>> buttonLists, final String userId) {
        Assert.notNull(titles);
        Assert.notNull(subTitles);
        Assert.notNull(imageUrls);
        Assert.notNull(buttonLists);
        Assert.notNull(userId);
        Assert.isTrue(titles.size() == subTitles.size(), "Element lists size must be the same");
        Assert.isTrue(titles.size() == imageUrls.size(), "Element lists size must be the same");
        Assert.isTrue(titles.size() == buttonLists.size(), "Element lists size must be the same");

        // Sending message will take time, so indicate typing first
        this.sendTypingOn(userId);

        List<PayloadElement> elements = new ArrayList<>();

        for (int i = 0; i < titles.size(); i++) {

            // Collect buttons
            List<com.eboy.conversation.outgoing.dto.Button> buttonDtos = buttonLists.get(i);
            List<Button> buttons = new ArrayList<>();

            for (com.eboy.conversation.outgoing.dto.Button buttonDto : buttonDtos) {

                Button button = Button.Builder.create()
                        .ofType(buttonDto.getType())
                        .withTitle(buttonDto.getTitle())
                        .withUrl(buttonDto.getUrl())
                        .withPayload(buttonDto.getIntent())
                        .build();

                buttons.add(button);
            }


            PayloadElement payloadElement = PayloadElement.Builder.create()
                    .withTitle(titles.get(i))
                    .withSubTitle(subTitles.get(i))
                    .withImageUrl(imageUrls.get(i))
                    .withButtons(buttons)
                    .build();

            elements.add(payloadElement);
        }


        BaseMessage message = AttachmentMessage.Builder.create()
                .withAttachment(
                        Attachment.Builder.create()
                                .withPayload(
                                        TemplatePayload.Builder.create()
                                                .withElements(elements)
                                                .build())
                                .withType(MessageType.TEMPLATE)
                                .build())
                .build();

        this.sendToUser(message, userId);
    }

    private void sendTypingOn(final String userId) {
        this.sendToUser(SenderAction.TYPING_ON, userId);
    }

    private void sendToUser(final SenderAction senderAction, final String userId) {

        Member recipient = Member.create(userId);
        SendBody sendBody = SendBody.Builder.create()
                .withSenderAction(senderAction)
                .withRecipient(recipient)
                .build();

        this.sendToUser(sendBody, userId);

    }

    private void sendToUser(final BaseMessage message, final String userId) {

        Member recipient = Member.create(userId);
        SendBody sendBody = SendBody.Builder.create()
                .withMessage(message)
                .withRecipient(recipient)
                .build();

        this.sendToUser(sendBody, userId);
    }

    private void sendToUser(final SendBody sendBody, final String userId) {

        HttpEntity<SendBody> requestEntity = new HttpEntity<>(sendBody);

        try {
            ResponseEntity<SendResponse> responseEntity = restTemplate.exchange(FB_MESSAGE_URL, HttpMethod.POST, requestEntity, SendResponse.class);

            logger.info("OUTGOING:\n" + sendBody);

            if (responseEntity == null) {
                logger.warning("Message wasn't sent: " + sendBody);
            }

        } catch (HttpClientErrorException ex) {
            logger.warning("Message wasn't sent: " + sendBody + ", Error: " + ex.getMessage());
        }

    }
}
