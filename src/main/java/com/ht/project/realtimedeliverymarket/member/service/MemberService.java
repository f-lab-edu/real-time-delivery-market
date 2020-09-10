package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.model.vo.MemberCache;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

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
  @Cacheable(value = "member", key="'memberInfo:' + '#account'")
  public MemberCache findMemberCacheByAccount(String account) {

    return MemberCache.from(memberRepository
            .findByAccount(account)
            .orElseThrow(IllegalArgumentException::new));
  }

}
