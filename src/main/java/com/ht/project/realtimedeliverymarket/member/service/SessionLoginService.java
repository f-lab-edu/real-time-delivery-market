package com.ht.project.realtimedeliverymarket.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ht.project.realtimedeliverymarket.cache.enumeration.SpringCacheType;
import com.ht.project.realtimedeliverymarket.cache.service.RedisCacheService;
import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.model.vo.MemberCache;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.Duration;

@Service
public class SessionLoginService implements LoginService{

  public static final String MEMBER_SESSION_KEY = "account";

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private RedisCacheService redisCacheService;

  @Autowired
  private ObjectMapper objectMapper;

  @Transactional
  @Override
  public void login(MemberLoginDto loginDto, HttpSession httpSession) {
    String sessionKey = MEMBER_SESSION_KEY;

    if (httpSession.getAttribute(sessionKey) != null) {
      throw new IllegalStateException("이미 로그인된 상태입니다.");
    }

    Member member = findMemberToLogin(loginDto);
    String account = member.getAccount();

    httpSession.setAttribute(sessionKey, account);
    String memberCache;

    try {
      memberCache = objectMapper.writeValueAsString(MemberCache.from(member));
    } catch (JsonProcessingException e) {

      throw new SerializationException("변환에 실패하였습니다.", e);
    }

    redisCacheService.set(
            redisCacheService.createSpringCacheKey(SpringCacheType.MEMBER, account),
            memberCache, Duration.ofMinutes(30L));
  }

  @Transactional
  private Member findMemberToLogin(MemberLoginDto loginDto) {

    Member member = memberRepository
            .findByAccount(loginDto.getAccount())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

    if (!member.getPassword().equals(loginDto.getPassword())) {

      throw new IllegalArgumentException();
    }

    return member;
  }
}
