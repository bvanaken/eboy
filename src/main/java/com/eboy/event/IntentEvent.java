package com.eboy.event;

import com.eboy.nlp.Intent;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class IntentEvent {

    public Long userId;
    public Platform platform;
    public Intent intent;

    public IntentEvent(Long userId, Intent intent, Platform platform) {
        this.intent = intent;
        this.userId = userId;
        this.platform = platform;
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public Intent getIntent() {
        return intent;
    }
}
