package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import com.ht.project.realtimedeliverymarket.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MemberService {

  @Autowired
  private MemberRepository memberRepository;

  public Long join(MemberJoinDto memberJoinDto) {

    Member member = Member.from(memberJoinDto);

    memberRepository.save(member);

    return member.getId();
  }
}
