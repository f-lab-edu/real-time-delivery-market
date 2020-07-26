package com.ht.project.realtimedeliverymarket.member;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
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
