package com.eboy.event;

import com.eboy.data.dto.Ad;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class LatestAdEvent {

    public Long userId;
    public Platform platform;
    public Ad data;

    public LatestAdEvent(Long userId, Ad data, Platform platform) {
        this.userId = userId;
        this.data = data;
        this.platform = platform;
    }
}
