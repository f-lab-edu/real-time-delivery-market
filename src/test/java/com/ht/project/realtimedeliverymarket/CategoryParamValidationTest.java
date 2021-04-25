package com.ht.project.realtimedeliverymarket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Optional;

public class CategoryParamValidationTest {

  private Validator validator;

  @BeforeEach
  public void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  private void validateBean(Object bean) throws AssertionError {
    Optional<ConstraintViolation<Object>> violation = validator.validate(bean).stream().findFirst();
    violation.ifPresent(v -> {
      throw new ValidationException(violation.get().getMessage());
    });
  }

  @Test
  @DisplayName("올바른 Category 매개변수가 주어지면 유효성 검사를 통과합니다.")
  public void givenRightCategoryParamValidationPassed() {

    //TODO 테스트 추가
  }
}
