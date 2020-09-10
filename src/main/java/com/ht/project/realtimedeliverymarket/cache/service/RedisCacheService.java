package com.ht.project.realtimedeliverymarket.cache.service;

import com.ht.project.realtimedeliverymarket.cache.enumeration.SpringCacheType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisCacheService {

  private final static String SPRING_CACHE_PREFIX = "spring:";
  private final static String SPRING_CACHE_SEPARATOR = ":";

  @Autowired
  @Qualifier("cacheStrRedisTemplate")
  private StringRedisTemplate cacheStrRedisTemplate;


  public String createSpringCacheKey (SpringCacheType cacheType, String suffix) {

    return SPRING_CACHE_PREFIX + cacheType.getValue() + SPRING_CACHE_SEPARATOR + suffix;
  }

  public void set(String key, String value, Duration ttl) {

    cacheStrRedisTemplate.opsForValue().set(key, value, ttl);
  }
}
