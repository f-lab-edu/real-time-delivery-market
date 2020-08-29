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
import org.springframework.test.util.ReflectionTestUtils;

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

  /**
   * ReflectionTestUtils
   * ReflectionTestUtils 객체는 단위 혹은 통합 테스트 시나리오상에서
   * reflection 기반 유틸리티 메소드들을 모아놓은 클래스입니다.
   *
   * invokeMethod()
   * 해당 메소드는 제공된 파라미터와 타깃 클래스에서 주어진 이름에 해당하는 메소드를 호출합니다.
   */
  @Test
  @DisplayName("계정이 중복되면 예외가 발생합니다.")
  public void verifyDuplicatedAccountExceptionThrown() {

    when(memberRepository.findByAccount("test1234"))
            .thenReturn(java.util.Optional.of(new Member()));

    assertThrows(IllegalStateException.class,
            () -> ReflectionTestUtils
                    .invokeMethod(memberService,
                            "verifyDuplicatedAccount",
                            "test1234"));

  }

}
