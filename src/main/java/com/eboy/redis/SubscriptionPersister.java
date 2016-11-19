package com.eboy.redis;

import com.eboy.redis.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@Service
public class SubscriptionPersister {

    private RedisTemplate<String, Object> redisTemplate;
    private RedisSerializer<Object> serializer;

    @Autowired
    public SubscriptionPersister(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.serializer = new JdkSerializationRedisSerializer();
    }

    public List<Subscription> getSubscriptions(final String id) {
        Assert.notNull(id);

        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();

        if (redisTemplate.hasKey(id)) {

            byte[] object = (byte[]) redisTemplate.opsForValue().get(id);

            List<Subscription> list = (List<Subscription>) serializer.deserialize(object);

            return list;

        }

        return null;
    }

    public void persistSubscription(final String id, Subscription subscription) {

        RedisSerializer<Object> serializer = new JdkSerializationRedisSerializer();
        List<Subscription> list = Arrays.asList(subscription);

        byte[] serialized = serializer.serialize(list);

        redisTemplate.opsForValue().set(id, serialized);

    }
}
