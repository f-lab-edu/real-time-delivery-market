package com.ht.project.realtimedeliverymarket.category.exception;

public class NotExistCategoryException extends RuntimeException{

  public NotExistCategoryException() {
  }

  public NotExistCategoryException(String message) {
    super(message);
  }

  public NotExistCategoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotExistCategoryException(Throwable cause) {
    super(cause);
  }

  public NotExistCategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
