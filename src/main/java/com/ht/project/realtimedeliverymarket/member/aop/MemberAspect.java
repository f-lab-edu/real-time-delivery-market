package com.ht.project.realtimedeliverymarket.member.aop;

import com.ht.project.realtimedeliverymarket.member.exception.UnauthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.ht.project.realtimedeliverymarket.member.service.SessionLoginService.MEMBER_SESSION_KEY;

@Aspect
@Component
public class MemberAspect {

  /**
   * RequestContextHolder.getRequestAttributes() : 현재 스레드에 바인딩된 RequestAttribute 객체를 반환합니다.
   * 존재하지 않으면 null 을 반환합니다.
   *
   * RequestContextHolder.currentRequestAttributes() : 현재 스레드에 바인딩된 RequestAttribute 객체를 반환합니다.
   * 존재하지 않으면 IllegalStateException 을 발생합니다.
   * @param joinPoint
   */
  @Before("@annotation(com.ht.project.realtimedeliverymarket.member.annotation.LoginCheck)")
  public void loginCheck(JoinPoint joinPoint) {

    if (((ServletRequestAttributes) RequestContextHolder
            .currentRequestAttributes())
            .getRequest()
            .getSession()
            .getAttribute(MEMBER_SESSION_KEY) == null) {

      throw new UnauthorizedException("로그인 정보가 존재하지 않습니다.");
    }
  }
}
