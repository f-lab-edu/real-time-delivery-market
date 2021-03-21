package com.ht.project.realtimedeliverymarket.member.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginDto {

  private String account;

  private String password;
}
