package com.ht.project.realtimedeliverymarket.member.model.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Value
@Builder
public class MemberJoinDto {

  @NotBlank(message = "아이디는 필수 입력사항입니다.")
  @Pattern(regexp = "^[0-9a-z].{6,10}$", message = "영문 소문자, 숫자 6~10자 이내로 입력하세요.")
  String account;

  @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
  @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,12}$",
          message = "영문 대소문자, 숫자, 특수문자를 각 1개 이상을 포함하고 8-12자 이내로 입력하세요.")
  String password;

  @NotBlank(message = "이름은 필수 입력사항입니다.")
  String name;

  @NotBlank(message = "이메일은 필수 입력사항입니다.")
  @Email(message = "이메일 형식에 맞지 않습니다.")
  String email;

  @NotBlank(message = "전화번호는 필수 입력사항입니다.")
  String phone;

  @NotBlank(message = "'서울시 강남구'의 형태로 입력해주세요.")
  String city;

  @NotBlank(message = "'논현동'의 형태로 입력해주세요.")
  String street;

  @NotBlank(message = "세부 주소를 입력해주세요.")
  String detail;

  @NotBlank(message = "우편번호를 입력해주세요.")
  String zipcode;
}
