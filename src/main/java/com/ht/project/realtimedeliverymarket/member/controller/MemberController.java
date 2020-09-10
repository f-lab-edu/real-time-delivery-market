package com.ht.project.realtimedeliverymarket.member.controller;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberJoinDto;
import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;
import com.ht.project.realtimedeliverymarket.member.service.LoginService;
import com.ht.project.realtimedeliverymarket.member.service.MemberService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

  @Autowired
  private LoginService loginService;

  @PostMapping
  public HttpStatus join(@RequestBody @Valid MemberJoinDto memberJoinDto) {

    memberService.join(memberJoinDto);

    return HttpStatus.CREATED;
  }

  @PostMapping("/login")
  public HttpStatus login(@RequestBody MemberLoginDto memberLoginDto, HttpSession httpSession) {

    loginService.login(memberLoginDto, httpSession);

    return HttpStatus.OK;
  }
}
