package com.eboy.event;

import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class UpdateEvent {

    public Long userId;
    public String message;
    public Platform platform;

    public UpdateEvent(Long userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
