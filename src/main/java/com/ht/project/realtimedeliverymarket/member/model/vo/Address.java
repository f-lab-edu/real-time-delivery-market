package com.ht.project.realtimedeliverymarket.member.model.vo;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  private String city;

  private String street;

  private String detail;

  private String zipcode;

}
