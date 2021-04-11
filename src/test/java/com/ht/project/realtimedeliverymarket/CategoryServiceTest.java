package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.category.exception.DuplicateCategoryException;
import com.ht.project.realtimedeliverymarket.category.exception.NotExistCategoryException;
import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import com.ht.project.realtimedeliverymarket.category.repository.CategoryRepository;
import com.ht.project.realtimedeliverymarket.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
  @DisplayName("카테고리가 중복되면 예외가 발생합니다.")
  public void givenDuplicatedCategoryNameThrowsException() {

    //given
    String categoryName = "고기류";

    //when
    when(categoryRepository.findByName(categoryName)).thenReturn(java.util.Optional.of(new Category()));

    //then
    assertThrows(DuplicateCategoryException.class,
            () -> categoryService.addCategory(null, categoryName));
  }

  @Test
  @DisplayName("메인 카테고리가 존재하지 않으면 예외가 발생합니다.")
  public void givenWrongParentIdWhenFindParentCategoryByIdThrowsException() {

    //given
    Long parentId = 1L;

    //when
    when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

    //then
    assertThrows(NotExistCategoryException.class, () -> categoryService.addCategory(parentId,
            "돼지고기"));
  }
}
