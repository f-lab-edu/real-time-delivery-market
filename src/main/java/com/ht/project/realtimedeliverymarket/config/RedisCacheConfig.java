package com.ht.project.realtimedeliverymarket.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
@PropertySource("application-cache.properties")
public class RedisCacheConfig extends CachingConfigurerSupport {

  @Value("${redis.cache.host}")
  private String host;

  @Value("${redis.cache.password:@null}")
  private String password;

  @Value("${redis.cache.port}")
  private int port;

  @Bean
  @Primary
  public RedisConnectionFactory cacheRedisConnectionFactory() {

    RedisStandaloneConfiguration redisStandaloneConfiguration =
            new RedisStandaloneConfiguration(host, port);
    redisStandaloneConfiguration.setPassword(password);

    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  @Override
  public CacheManager cacheManager() {

    return RedisCacheManager
            .RedisCacheManagerBuilder
            .fromConnectionFactory(cacheRedisConnectionFactory())
            .cacheDefaults(RedisCacheConfiguration
                    .defaultCacheConfig()
                    .serializeValuesWith(RedisSerializationContext
                            .SerializationPair
                            .fromSerializer(new GenericJackson2JsonRedisSerializer()))
                    .prefixCacheNameWith("spring:")
                    .entryTtl(Duration.ofSeconds(60L)))
            .build();

  }

  @Bean("cacheRedisTemplate")
  public RedisTemplate<String, Object> cacheRedisTemplate() {

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    redisTemplate.setConnectionFactory(cacheRedisConnectionFactory());
    redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setKeySerializer(new StringRedisSerializer());

    redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

    return redisTemplate;
  }

  @Bean("cacheStrRedisTemplate")
  public StringRedisTemplate cacheStrRedisTemplate() {

    return new StringRedisTemplate(cacheRedisConnectionFactory());
  }
}
