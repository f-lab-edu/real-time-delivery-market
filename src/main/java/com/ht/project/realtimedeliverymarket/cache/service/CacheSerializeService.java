package com.ht.project.realtimedeliverymarket.cache.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

@Service
public class CacheSerializeService {

  @Autowired
  @Qualifier("cacheObjectMapper")
  private ObjectMapper objectMapper;

  public <T> String createJsonStringFrom(T object) {

    try {
      return objectMapper.writeValueAsString(object);

    } catch (JsonProcessingException e) {

      throw new SerializationException("변환에 실패하였습니다.", e);
    }
  }

}
