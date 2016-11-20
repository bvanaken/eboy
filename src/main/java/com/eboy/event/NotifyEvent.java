package com.eboy.event;

import com.eboy.conversation.incoming.SearchQuery;
import com.eboy.data.dto.Ad;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class NotifyEvent {

    public Long userId;
    public Platform platform;
    public Ad data;
    public SearchQuery searchQuery;

    public NotifyEvent(Long userId, Platform platform, Ad data, SearchQuery searchQuery) {
        this.userId = userId;
        this.platform = platform;
        this.data = data;
        this.searchQuery = searchQuery;
    }

    public NotifyEvent() {
        //
    }

    public Long getUserId() {
        return userId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public SearchQuery getSearchQuery() {
        return searchQuery;
    }

    public Ad getData() {
        return data;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setData(Ad data) {
        this.data = data;
    }

    public void setSearchQuery(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }
}
