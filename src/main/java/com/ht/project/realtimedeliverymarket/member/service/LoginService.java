package com.ht.project.realtimedeliverymarket.member.service;

import com.ht.project.realtimedeliverymarket.member.model.dto.MemberLoginDto;

import javax.servlet.http.HttpSession;

public interface LoginService {

  void login(MemberLoginDto loginDto, HttpSession httpSession);

}
