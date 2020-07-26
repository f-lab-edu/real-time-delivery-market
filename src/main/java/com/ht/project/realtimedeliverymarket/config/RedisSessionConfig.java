package com.ht.project.realtimedeliverymarket.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * @EnableRedisHttpSession: 해당 어노테이션의 경우 SpringSessionRepositoryFilter 빈을 생성합니다.
 * 해당 필터는 HttpSession 구현체를 Redis가 지원하도록 바꾸는 역할을 합니다.
 * SpringSessionRepositoryFilter를 스프링이 로딩하게 해주어야 합니다.
 * 그러므로 서블릿 컨테이너가 매 요청마다 해당 필터를 사용할 수 있도록 하는 작업이 필요합니다.
 * 스프링이 이를 로딩할 수 있도록 Spring Session은 AbstractHttpSessionApplicationInitializer를 제공합니다.
 * 해당 어노테이션을 활용하기 위해서는 @Configuration 클래스 내부에
 * 하나 이상의 RedisConnectionFactory 가 제공 되어야 합니다.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800) //1800이 default
public class RedisSessionConfig extends AbstractHttpSessionApplicationInitializer {

  @Value("${redis.session.host}")
  private String host;

  @Value("${redis.session.password:@null}")
  private String password;

  @Value("${redis.session.port}")
  private int port;

  /**
   * Redis 에 접속하기 위한 Connection 들을 생성하는 팩토리 Object 입니다.
   * Thread-safe합니다.
   * 해당 프로젝트에서는 LettuceConnectionFactory 를 사용합니다.
   * Lettuce 는 Netty (비동기 이벤트 기반 고성능 네트워크 프레임워크) 기반의 Redis 클라이언트로써
   * 비동기로 요청을 처리하기 때문에 고성능을 자랑합니다.
   */
  @Bean("sessionRedisConnectionFactory")
  public RedisConnectionFactory sessionRedisConnectionFactory() {

    RedisStandaloneConfiguration redisStandaloneConfiguration =
            new RedisStandaloneConfiguration(host, port);
    redisStandaloneConfiguration.setPassword(password);

    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  /**
   * RedisTemplate: Redis 데이터 엑세스 코드를 단순화하게 도와주는 클래스입니다.
   * 주어진 객체들과 레디스 저장소 내부의 이진 데이터 사이에서 자동적으로 직렬화와 역직렬화를 수행합니다.
   * 문자열을 주로 다루는 경우, StringRedisTemplate 사용을 고려하는 것이 좋습니다.
   * RedisCallback interface 를 구현하여 Redis 접근을 지원하는 메소드를 실행합니다.
   * 일단 구성이 되면 이 클래스는 Thread-safe 합니다.
   * Default 값으로 JDKSerializationRedisSerializer 를 사용합니다.
   * @param sessionRedisConnectionFactory
   * @return
   */
  @Bean("sessionRedisTemplate")
  public RedisTemplate<String, Object> sessionRedisTemplate(
          @Qualifier("sessionRedisConnectionFactory") RedisConnectionFactory sessionRedisConnectionFactory) {

    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(sessionRedisConnectionFactory);
    redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
    redisTemplate.setKeySerializer(new StringRedisSerializer());

    return redisTemplate;
  }

  @Bean
  RedisSerializer<Object> springSessionDefaultRedisSerializer() {

    return new GenericJackson2JsonRedisSerializer();
  }
}
