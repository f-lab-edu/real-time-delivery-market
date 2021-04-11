package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import com.ht.project.realtimedeliverymarket.category.repository.CategoryRepository;
import com.ht.project.realtimedeliverymarket.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Test
  @DisplayName("메인 카테고리가 중복되면 예외가 발생합니다.")
  public void insertDuplicatedMainCategoryThrowsException() {

    when(categoryRepository.findByName("고기류")).thenReturn(java.util.Optional.of(new Category()));

    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils
            .invokeMethod(categoryService,
                    "addMainCategory",
                    "고기류"));
  }

  @Test
  @DisplayName("서브 카테고리가 중복되면 예외가 발생합니다.")
  public void insertDuplicatedSubCategoryThrowsException() {

    when(categoryRepository.findByName("돼지고기")).thenReturn(java.util.Optional.of(new Category()));

    assertThrows(IllegalArgumentException.class, () -> ReflectionTestUtils
            .invokeMethod(categoryService,
                    "addSubCategory",
                    1L, "돼지고기"));
  }

  @Test
  @DisplayName("메인 카테고리가 존재하지 않으면 예외가 발생합니다.")
  public void insertSubCategoryThrowsException() {

    when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () ->
            ReflectionTestUtils
                    .invokeMethod(categoryService, "addSubCategory", 1L, "돼지고기"));
  }
}
