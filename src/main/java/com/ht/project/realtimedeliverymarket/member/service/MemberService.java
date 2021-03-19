package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.model.vo.MemberInfo;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Member join(MemberJoinDto memberJoinDto) {

    Member member = Member.from(memberJoinDto);

    verifyDuplicatedAccount(memberJoinDto.getAccount());

    member = memberRepository.save(member);

    return member;
  }

  /**
   * Optional.ifPresent()
   * Optional 객체가 non-null 이 경우에 인자로 넘긴 함수를 실행하는 메서드입니다.
   * Optional 객체가 null 이면 인자로 넘긴 함수는 실행되지 않습니다.
   *
   * @param account 사용자 ID
   */
  private void verifyDuplicatedAccount(String account) {

    Optional<Member> member = memberRepository.findByAccount(account);

    member.ifPresent(s -> {
      throw new IllegalStateException("중복된계정입니다.");
    });

  }

  @Transactional
  @Cacheable(value = "memberInfo", key="'memberInfo:' + '#loginDto.account")
  public MemberInfo findMemberInfo(MemberLoginDto loginDto) {

    Member member = memberRepository
            .findByAccount(loginDto.getAccount())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));

    if (!member.getPassword().equals(loginDto.getPassword())) {

      throw new IllegalArgumentException();
    }

    return MemberInfo.from(member);
  }
}
