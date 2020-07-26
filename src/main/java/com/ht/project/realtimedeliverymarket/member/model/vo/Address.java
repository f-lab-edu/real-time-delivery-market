package com.ht.project.realtimedeliverymarket.member;

import lombok.Value;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class Address {

  String city;

  String street;

  String detail;

  String zipcode;

}
