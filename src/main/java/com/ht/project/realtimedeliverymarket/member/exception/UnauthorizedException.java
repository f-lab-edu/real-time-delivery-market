package com.ht.project.realtimedeliverymarket.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class UnauthorizedException extends RuntimeException{

  public UnauthorizedException(String message) {
    super(message);
  }
}
