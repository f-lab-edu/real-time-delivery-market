package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class SessionLoginService implements LoginService{

  public static final String MEMBER_SESSION_KEY = "account";

  private final MemberService memberService;

  @Transactional
  @Override
  public void login(MemberLoginDto loginDto, HttpSession httpSession) {

    if (httpSession.getAttribute(MEMBER_SESSION_KEY) != null) {
      throw new IllegalStateException("이미 로그인된 상태입니다.");
    }

    httpSession.setAttribute(MEMBER_SESSION_KEY, memberService.findMemberInfo(loginDto).getAccount());
  }
}
