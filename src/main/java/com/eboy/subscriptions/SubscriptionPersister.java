package com.eboy.subscriptions;

import com.eboy.subscriptions.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SubscriptionPersister {

    private RedisTemplate<String, Object> redisTemplate;
    ObjectMapper mapper;

    @Autowired
    public SubscriptionPersister(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.mapper = new ObjectMapper();
    }

    public List<Subscription> getSubscriptions(final String key) {
        Assert.notNull(key);

        String object = (String) redisTemplate.opsForValue().get(key);

        if (object != null) {

            try {
                List<Subscription> list = mapper.readValue(object, new TypeReference<List<Subscription>>() {
                });

                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Set<String> getKeys() {

        Set<String> keys = redisTemplate.keys("*");

        return keys;
    }

    public void persistSubscription(final String key, Subscription subscription) {

        List<Subscription> list = this.getSubscriptions(key);

        if (list == null) {
            list = new ArrayList<>();
        } else {

            for (Subscription oldSub : list) {

                // if user exists for this key, replace it
                if (oldSub.getUserId().equals(subscription.getUserId())) {
                    list.set(list.indexOf(oldSub), subscription);
                }
            }
        }
        if (!list.contains(subscription)) {
            list.add(subscription);
        }

        try {
            String jsonInString = mapper.writeValueAsString(list);
            redisTemplate.opsForValue().set(key, jsonInString);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void persistSubscriptions(final String key, List<Subscription> subscriptions) {

        List<Subscription> list = this.getSubscriptions(key);

        if (list == null) {
            list = new ArrayList<>();
        }
        list.addAll(subscriptions);

        try {
            String jsonInString = mapper.writeValueAsString(list);
            redisTemplate.opsForValue().set(key, jsonInString);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
