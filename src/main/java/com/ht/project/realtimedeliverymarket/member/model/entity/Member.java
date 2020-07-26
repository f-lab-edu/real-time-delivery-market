package com.ht.project.realtimedeliverymarket.member;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER")
@NoArgsConstructor
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String account;

  private String password;


  private String email;

  @Embedded
  @AttributeOverrides({
          @AttributeOverride(name = "city", column = @Column(name = "ADDRESS_CITY")),
          @AttributeOverride(name = "street", column = @Column(name = "ADDRESS_CITY")),
          @AttributeOverride(name = "detail", column = @Column(name = "ADDRESS_DETAIL")),
          @AttributeOverride(name = "zipcode", column = @Column(name = "ADDRESS_ZIPCODE"))
  })
  private Address address;

  private String phone;


}
