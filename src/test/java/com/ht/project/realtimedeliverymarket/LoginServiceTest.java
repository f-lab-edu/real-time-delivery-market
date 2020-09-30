package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.member.service.SessionLoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private SessionLoginService loginService;

  @Test
  @DisplayName("로그인할 회원의 계정을 찾지 못하면 예외가 발생합니다. ")
  public void findMemberToLoginNotFoundAccount() {

    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test1234!");

    Mockito.when(memberRepository.findByAccount("test1234")).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils
            .invokeMethod(loginService,
                    "findMemberToLogin",
                    loginDto));
  }

  @Test
  @DisplayName("로그인할 회원의 계정과 패스워드가 일치합니다.")
  public void findMemberToLoginMatchComplete() {

    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test1234!");

    Mockito.when(memberRepository.findByAccount(loginDto.getAccount()))
            .thenReturn(Optional.ofNullable(Member.builder()
                    .account("test1234")
                    .password("Test1234!").build()));

    assertEquals("test1234", memberRepository.findByAccount(loginDto.getAccount()).get().getAccount());
    assertEquals("Test1234!", memberRepository.findByAccount(loginDto.getAccount()).get().getPassword());

  }

  @Test
  @DisplayName("로그인할 회원의 패스워드가 일치하지 못하면 예외가 발생합니다.")
  public void findMemberToLoginNotMatchPassword() {

    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test1234!");

    Mockito.when(memberRepository.findByAccount(loginDto.getAccount()))
            .thenReturn(Optional.ofNullable(Member.builder()
                    .account("test1234")
                    .password("Test2345!").build()));

    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils
            .invokeMethod(loginService,
                    "findMemberToLogin",
                    loginDto));
  }
}
