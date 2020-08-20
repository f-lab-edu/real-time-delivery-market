package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberService memberService;


  @Test
  @DisplayName("회원가입 테스트")
  public void joinTestShouldPass() {

    MemberJoinDto memberJoinDto = MemberJoinDto.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .city("서울시 강남구")
            .street("논현동")
            .detail("테스트타워 101호")
            .zipcode("123-123")
            .build();

    when(memberRepository.save(any(Member.class))).thenReturn(Member.from(memberJoinDto));

    Member member = memberService.join(memberJoinDto);

    assertEquals("test1234", member.getAccount());
    assertEquals("Test1234!", member.getPassword());
    assertEquals("홍길동", member.getName());
    assertEquals("test1234@gmail.com", member.getEmail());
    assertEquals("010-1234-1234", member.getPhone());
    assertEquals("서울시 강남구", member.getAddress().getCity());
    assertEquals("논현동", member.getAddress().getStreet());
    assertEquals("테스트타워 101호", member.getAddress().getDetail());
    assertEquals("123-123", member.getAddress().getZipcode());

  }

  @Test
  @DisplayName("계정이 중복되면 예외가 발생합니다.")
  public void verifyDuplicatedAccountExceptionThrown() throws NoSuchMethodException{

    when(memberRepository.findByAccount(any(String.class))).thenReturn(java.util.Optional.of(new Member()));

    Method verifyDuplicatedAccount = memberService.getClass()
              .getDeclaredMethod("verifyDuplicatedAccount", String.class);

    verifyDuplicatedAccount.setAccessible(true);

    assertThrows(InvocationTargetException.class,
            () -> verifyDuplicatedAccount.invoke(memberService, "test1234"));

  }

}
