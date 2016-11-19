package com.eboy.platform.facebook.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.eboy.platform.facebook.update.Member;

public class SendBody {

    private BaseMessage message;
    private Member recipient;

    @JsonProperty("sender_action")
    private String senderAction;

    private SendBody(BaseMessage message, Member recipient, String senderAction) {
        this.message = message;
        this.recipient = recipient;
        this.senderAction = senderAction;
    }

    public BaseMessage getMessage() {
        return message;
    }

    public Member getRecipient() {
        return recipient;
    }

    public String getSenderAction() {
        return senderAction;
    }

    @Override
    public String toString() {
        return "SendBody{" +
                "message=" + message +
                ", recipient=" + recipient +
                ", senderAction=" + senderAction +
                '}';
    }

    public static class Builder {
        private BaseMessage message;
        private Member recipient;
        private String senderAction;

        public static Builder create() {
            return new Builder();
        }

        public Builder withMessage(BaseMessage message) {
            this.message = message;
            return this;
        }

        public Builder withRecipient(Member recipient) {
            this.recipient = recipient;
            return this;
        }

        public Builder withSenderAction(SenderAction senderAction) {
            if (senderAction != null) {
                this.senderAction = senderAction.getValue();
            }
            return this;
        }

        public SendBody build() {
            return new SendBody(message, recipient, senderAction);
        }
    }
}
