package com.eboy.platform.facebook.message;

public enum SenderAction {
    MARK_SEEN("mark_seen"), TYPING_ON("typing_on"), TYPING_OFF("typing_off");

    private String value;

    SenderAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
