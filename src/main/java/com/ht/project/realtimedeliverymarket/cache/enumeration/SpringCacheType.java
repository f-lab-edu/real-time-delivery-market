package com.ht.project.realtimedeliverymarket.cache.enumeration;

import lombok.Getter;

/**
 * Spring Cache 를 이용하는 캐시의 종류를
 * 가리키는 열거타입입니다.
 */
@Getter
public enum SpringCacheType {

  MEMBER("memberInfo");

  private final String value;

  SpringCacheType(String value) {
    this.value = value;
  }

  public static SpringCacheType from(String value) {

    switch (value) {

      case "memberInfo": return MEMBER;
      default: throw new IllegalArgumentException("Unknown value: " + value);
    }
  }

}
