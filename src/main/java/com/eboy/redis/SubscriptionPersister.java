package com.eboy.redis;

import com.eboy.redis.model.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

@Service
public class SubscriptionPersister {

    private RedisTemplate<String, Object> redisTemplate;
    private RedisSerializer<Object> serializer;
    ObjectMapper mapper;

    @Autowired
    public SubscriptionPersister(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.serializer = new JdkSerializationRedisSerializer();
        this.mapper = new ObjectMapper();
    }

    public List<Subscription> getSubscriptions(final String id) {
        Assert.notNull(id);

        String object = (String) redisTemplate.opsForValue().get(id);

        try {
            List<Subscription> list = mapper.readValue(object, new TypeReference<List<Subscription>>() {
            });

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void persistSubscription(final String id, Subscription subscription) {

        List<Subscription> list = this.getSubscriptions(id);
        list.add(subscription);

        try {
            String jsonInString = mapper.writeValueAsString(list);
            redisTemplate.opsForValue().set(id, jsonInString);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
