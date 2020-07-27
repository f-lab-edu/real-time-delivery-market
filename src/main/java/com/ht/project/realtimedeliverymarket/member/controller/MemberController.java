package com.ht.project.realtimedeliverymarket.member.controller;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

  @Autowired
  private MemberService memberService;

  @PostMapping
  public HttpStatus join(@RequestBody MemberJoinDto memberJoinDto) {

    memberService.join(memberJoinDto);

    return HttpStatus.CREATED;
  }
}
