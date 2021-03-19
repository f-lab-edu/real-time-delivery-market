package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.model.vo.Address;
import com.ht.project.realtimedeliverymarket.member.model.vo.MemberInfo;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

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

  private Member member;

  @BeforeEach
  public void setUp() {

    member = Member.builder()
            .account("test1234")
            .password("Test1234!")
            .name("홍길동")
            .email("test1234@gmail.com")
            .phone("010-1234-1234")
            .address(new Address("서울시 강남구",
                    "논현동",
                    "테스트타워 101호",
                    "123-123"))
            .build();
  }

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

    Member joinMember = memberService.join(memberJoinDto);

    assertEquals(member.getAccount(), joinMember.getAccount());
    assertEquals(member.getPassword(), joinMember.getPassword());
    assertEquals(member.getName(), joinMember.getName());
    assertEquals(member.getEmail(), joinMember.getEmail());
    assertEquals(member.getPhone(), joinMember.getPhone());
    assertEquals(member.getAddress().getCity(), joinMember.getAddress().getCity());
    assertEquals(member.getAddress().getStreet(), joinMember.getAddress().getStreet());
    assertEquals(member.getAddress().getDetail(), joinMember.getAddress().getDetail());
    assertEquals(member.getAddress().getZipcode(), joinMember.getAddress().getZipcode());

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

    String account = "test1234";

    when(memberRepository.findByAccount(account)).thenReturn(Optional.of(member));

    assertThrows(IllegalStateException.class,
            () -> ReflectionTestUtils
                    .invokeMethod(memberService,
                            "verifyDuplicatedAccount",
                            account));

  }

  @Test
  @DisplayName("입력한 ID와 패스워드가 일치하지 않으면 IllegalArgumentException이 발생합니다.")
  public void givenWrongPasswordWhenFindMemberInfoThenThrowsIllegalArgumentException() {

    //given
    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test2345@");

    //when
    when(memberRepository.findByAccount(loginDto.getAccount()))
            .thenReturn(Optional.of(member));

    //then
    assertThrows(IllegalArgumentException.class,
            () -> ReflectionTestUtils.invokeMethod(memberService, "findMemberInfo", loginDto));
  }

  @Test
  @DisplayName("일치하는 로그인 정보입력하면 올바른 회원정보를 반환합니다.")
  public void givenRightLoginInfoWhenFindMemberInfoThenPassed() {

    //given
    MemberLoginDto loginDto = new MemberLoginDto("test1234", "Test1234!");

    //when
    when(memberRepository.findByAccount(loginDto.getAccount())).thenReturn(Optional.of(member));

    //then
    MemberInfo memberInfo = memberService.findMemberInfo(loginDto);

    assertEquals(member.getAccount(), memberInfo.getAccount());
    assertEquals(member.getName(), memberInfo.getName());
    assertEquals(member.getEmail(), memberInfo.getEmail());
    assertEquals(member.getPhone(), memberInfo.getPhone());
    assertEquals(member.getAddress().getCity(), memberInfo.getCity());
    assertEquals(member.getAddress().getStreet(), memberInfo.getStreet());
    assertEquals(member.getAddress().getDetail(), memberInfo.getDetail());
    assertEquals(member.getAddress().getZipcode(), memberInfo.getZipcode());
  }
}
