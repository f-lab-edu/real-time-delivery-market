package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
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
  public void 회원등록() {

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
  }
}
