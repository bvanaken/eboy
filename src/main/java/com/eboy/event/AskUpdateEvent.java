package com.eboy.event;

import com.eboy.conversation.incoming.SearchQuery;
import com.eboy.platform.Platform;

/**
 * Created by root1 on 19/11/16.
 */
public class AskUpdateEvent {

    public Long userId;
    public Platform platform;
    public SearchQuery searchQuery;

    public AskUpdateEvent(Long userId, SearchQuery searchQuery, Platform platform) {
        this.userId = userId;
        this.searchQuery = searchQuery;
        this.platform = platform;
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
}
