package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class MemberServiceTest {

  @Autowired
  private MemberService memberService;

  @Autowired
  private MemberRepository memberRepository;

  @Test
  public void 회원가입() {

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

    Long saveId = memberService.join(memberJoinDto);

    assertEquals("test1234", memberRepository.findById(saveId).get().getAccount());
    assertEquals("Test1234!", memberRepository.findById(saveId).get().getPassword());
    assertEquals("홍길동", memberRepository.findById(saveId).get().getName());
    assertEquals("test1234@gmail.com", memberRepository.findById(saveId).get().getEmail());
    assertEquals("010-1234-1234", memberRepository.findById(saveId).get().getPhone());
    assertEquals("서울시 강남구", memberRepository.findById(saveId).get().getAddress().getCity());
    assertEquals("논현동", memberRepository.findById(saveId).get().getAddress().getStreet());
    assertEquals("테스트타워 101호", memberRepository.findById(saveId).get().getAddress().getDetail());
    assertEquals("123-123", memberRepository.findById(saveId).get().getAddress().getZipcode());

  }

}
