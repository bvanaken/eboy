package com.eboy.event;

import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class ImageRecognitionEvent {

    public Long userId;
    public Platform platform;
    public String keywords;

    public ImageRecognitionEvent(Long userId, Platform platform, String keywords) {
        this.userId = userId;
        this.platform = platform;
        this.keywords = keywords;
    }
}
