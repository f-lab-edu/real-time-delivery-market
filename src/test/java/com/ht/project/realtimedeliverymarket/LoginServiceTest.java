package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import com.ht.project.realtimedeliverymarket.member.model.vo.MemberInfo;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import com.ht.project.realtimedeliverymarket.member.service.SessionLoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.util.ReflectionTestUtils;

import static com.ht.project.realtimedeliverymarket.member.service.SessionLoginService.MEMBER_SESSION_KEY;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

  @Mock
  private MemberService memberService;

  @InjectMocks
  private SessionLoginService loginService;

  private MemberInfo memberInfo;

  @BeforeEach
  public void setUp() {

    this.memberInfo = MemberInfo.builder()
            .id(String.valueOf(1L))
            .account("test1234")
            .name("Joe")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울특별시 강남구")
            .street("논현동")
            .detail("테스트 타워 101호")
            .zipcode("123-123")
            .points(String.valueOf(1000))
            .build();
  }

  @Test
  @DisplayName("이미 사용자가 로그인한 상태라면 IllegalStateException이 발생합니다.")
  public void givenMemberLoginDtoWhenLoginThenThrowIllegalStateException() {

    //given
    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test1234!");
    MockHttpSession httpSession = new MockHttpSession();
    httpSession.setAttribute(MEMBER_SESSION_KEY, "test1234");

    //when

    //then
    assertThrows(IllegalStateException.class, () -> ReflectionTestUtils
            .invokeMethod(loginService, "login", loginDto));
  }

  @Test
  @DisplayName("로그인에 성공합니다.")
  public void givenMemberLoginDtoWhenLoginThenComplete() {

    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test1234!");

    Mockito.when(memberService.findMemberInfo(loginDto))
            .thenReturn(memberInfo);

    loginService.login(loginDto, new MockHttpSession());
  }
}
