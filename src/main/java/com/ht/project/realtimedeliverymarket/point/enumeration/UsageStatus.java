package com.ht.project.realtimedeliverymarket.point.enumeration;

public enum UsageStatus {

  USED, NOT_USED, EXPIRED;

  public static UsageStatus valueOf(UsageStatus status) {

    switch (status) {
      case USED: return UsageStatus.USED;
      case NOT_USED: return UsageStatus.NOT_USED;
      case EXPIRED: return UsageStatus.EXPIRED;

      default: throw new AssertionError("알 수 없는 상태: " + status);
    }
  }
}
