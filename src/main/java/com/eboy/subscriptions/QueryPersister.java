package com.eboy.subscriptions;

import com.eboy.conversation.incoming.SearchQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class QueryPersister {

    private RedisTemplate<String, Object> redisTemplate;
    ObjectMapper mapper;

    @Autowired
    public QueryPersister(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.mapper = new ObjectMapper();
    }

    public SearchQuery getSearchQuery(final Long userId) {

        String object = (String) redisTemplate.opsForValue().get(String.valueOf(userId));

        if (object != null) {

            try {
                SearchQuery query = mapper.readValue(object, new TypeReference<SearchQuery>() {
                });

                return query;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void persistSearchQuery(final Long userId, SearchQuery query) {

        try {
            String jsonInString = mapper.writeValueAsString(query);
            redisTemplate.opsForValue().set(String.valueOf(userId), jsonInString);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
