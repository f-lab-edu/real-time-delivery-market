package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

  private void verifyDuplicatedAccount(String account) {

    List<Member> members = memberRepository.findByAccount(account);

    if(!members.isEmpty()) {
      throw new IllegalStateException("중복된 계정입니다.");
    }
  }
}
