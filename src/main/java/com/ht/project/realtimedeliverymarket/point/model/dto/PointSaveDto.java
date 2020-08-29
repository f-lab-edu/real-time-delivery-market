package com.ht.project.realtimedeliverymarket.point.model.dto;


import lombok.Value;

import java.math.BigDecimal;


@Value
public class PointSaveDto {

  String content;

  BigDecimal value;

  long expire;
}
