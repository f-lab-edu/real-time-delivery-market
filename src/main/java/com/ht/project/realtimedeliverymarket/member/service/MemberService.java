package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  @Transactional
  public Long join(MemberJoinDto memberJoinDto) {

    Member member = Member.from(memberJoinDto);

    verifyDuplicatedAccount(memberJoinDto.getAccount());
    memberRepository.save(member);

    return member.getId();
  }

  /**
   * Optional.isPresent()
   * 해당 값이 존재하는지 여부를 파악할 수 있고, 있으면 true 를 반환하고, 없으면 false 를 반환합니다.
   *
   * @param account 사용자 ID
   */
  private void verifyDuplicatedAccount(String account) {

    Optional<Member> member = memberRepository.findByAccount(account);

    if(member.isPresent()) {
      throw new IllegalStateException("중복된 계정입니다.");
    }
  }
}
