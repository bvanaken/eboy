package com.eboy.event;

import com.eboy.mv.model.Recognition;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class ImageRecognitionEvent {

    public Long userId;
    public Platform platform;
    public Recognition recognition;

    public ImageRecognitionEvent(Long userId, Platform platform, Recognition recognition) {
        this.userId = userId;
        this.platform = platform;
        this.recognition = recognition;
    }
}
