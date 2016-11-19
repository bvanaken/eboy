
package com.eboy.platform.facebook.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {

    @JsonProperty("sender")
    private Member sender;

    @JsonProperty("recipient")
    private Member recipient;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("message")
    private Message message;

    @JsonProperty("account_linking")
    private AccountLink accountLinking;

    @JsonProperty("postback")
    private Postback postback;

    public Event() {
    }

    public Event(Member sender, Member recipient, Long timestamp, AccountLink accountLinking) {
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.accountLinking = accountLinking;
    }

    public Event(Member sender, Member recipient, Long timestamp, Message message) {
        this.sender = sender;
        this.recipient = recipient;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Member getSender() {
        return sender;
    }

    public Member getRecipient() {
        return recipient;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Message getMessage() {
        return message;
    }

    public AccountLink getAccountLinking() {
        return accountLinking;
    }

    public Postback getPostback() {
        return postback;
    }

    @Override
    public String toString() {
        return "Event{" +
                "sender=" + sender +
                ", recipient=" + recipient +
                ", timestamp=" + timestamp +
                ", message=" + message +
                ", accountLinking=" + accountLinking +
                ", postback=" + postback +
                '}';
    }
}
