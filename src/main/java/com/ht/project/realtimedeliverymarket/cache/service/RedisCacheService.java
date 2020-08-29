package com.ht.project.realtimedeliverymarket.cache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.project.realtimedeliverymarket.cache.enumeration.SpringCacheType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisCacheService {

  private final String SPRING_CACHE_PREFIX = "spring:";
  private final String SPRING_CACHE_SEPARATOR = ":";

  @Autowired
  @Qualifier("cacheStrRedisTemplate")
  private StringRedisTemplate cacheStrRedisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  public String createSpringCacheKey (SpringCacheType cacheType, String suffix) {

    return SPRING_CACHE_PREFIX + cacheType.getValue() + SPRING_CACHE_SEPARATOR + suffix;
  }

  public <T> String createJsonStringFrom(T object) {

    try {
      return objectMapper.writeValueAsString(object);

    } catch (JsonProcessingException e) {

      throw new SerializationException("변환에 실패하였습니다.", e);
    }
  }

  public void setCacheAsString(String key, String value, Duration ttl) {

    cacheStrRedisTemplate.opsForValue().set(key, value, ttl);
  }
}
