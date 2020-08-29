package com.ht.project.realtimedeliverymarket.member.model.vo;

import com.ht.project.realtimedeliverymarket.member.model.entity.Member;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MemberCache {

  String id;

  String account;

  String name;

  String email;

  String phone;

  String city;

  String street;

  String detail;

  String zipcode;

  String points;

  public static MemberCache from(Member member) {

    Address address = member.getAddress();

    return MemberCache.builder()
            .id(String.valueOf(member.getId()))
            .account(member.getAccount())
            .name(member.getName())
            .email(member.getEmail())
            .phone(member.getPhone())
            .city(address.getCity())
            .street(address.getStreet())
            .detail(address.getDetail())
            .zipcode(address.getZipcode())
            .points(String.valueOf(member.getPointTotal()))
            .build();
  }
}
