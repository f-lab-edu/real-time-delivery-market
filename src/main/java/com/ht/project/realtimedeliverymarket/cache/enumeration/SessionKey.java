package com.ht.project.realtimedeliverymarket.cache.enumeration;

import lombok.Getter;

@Getter
public enum SessionKey {

  MEMBER("account");

  private final String value;

  SessionKey(String value) {
    this.value = value;
  }

  public static SessionKey from(String value) {

    switch (value) {
      case "account": return MEMBER;
      default: throw new IllegalArgumentException("Unknown value: " + value);
    }
  }
}
