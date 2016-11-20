package com.eboy.platform.telegram.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;
import java.util.Locale;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("message_id")
    private Integer messageId;
    private String text;
    private Chat chat;
    private Sender from;

    private MessageEntity[] entities;

    private Integer date;

    @JsonProperty("forward_from")
    private Sender forwardFrom;

    @JsonProperty("forward_from_chat")
    private Chat forwardFromChat;

    @JsonProperty("forward_date")
    private Integer forwardDate;

    private TelegramFile[] photo;

    private Location location;

    public Message() {
        // jersey
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Sender getFrom() {
        return from;
    }

    public void setFrom(Sender from) {
        this.from = from;
    }

    public MessageEntity[] getEntities() {
        return entities;
    }

    public void setEntities(MessageEntity[] entities) {
        this.entities = entities;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Sender getForwardFrom() {
        return forwardFrom;
    }

    public void setForwardFrom(Sender forwardFrom) {
        this.forwardFrom = forwardFrom;
    }

    public Chat getForwardFromChat() {
        return forwardFromChat;
    }

    public void setForwardFromChat(Chat forwardFromChat) {
        this.forwardFromChat = forwardFromChat;
    }

    public Integer getForwardDate() {
        return forwardDate;
    }

    public void setForwardDate(Integer forwardDate) {
        this.forwardDate = forwardDate;
    }

    public TelegramFile[] getPhoto() {
        return photo;
    }

    public void setPhoto(TelegramFile[] photo) {
        this.photo = photo;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", text='" + text + '\'' +
                ", chat=" + chat +
                ", from=" + from +
                ", entities=" + Arrays.toString(entities) +
                ", date=" + date +
                ", forwardFrom=" + forwardFrom +
                ", forwardFromChat=" + forwardFromChat +
                ", forwardDate=" + forwardDate +
                '}';
    }
}
