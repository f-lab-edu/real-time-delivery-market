package com.ht.project.realtimedeliverymarket;

import com.ht.project.realtimedeliverymarket.category.exception.DuplicateCategoryException;
import com.ht.project.realtimedeliverymarket.category.model.dto.CategoryParam;
import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import com.ht.project.realtimedeliverymarket.category.repository.CategoryRepository;
import com.ht.project.realtimedeliverymarket.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Test
  @DisplayName("메인 카테고리 이름이 중복되면 DuplicateCategoryException이 발생합니다.")
  public void givenDuplicatedMainCategoryNameThrowsException() {

    //given
    List<String> categoryNames = new ArrayList<>();
    categoryNames.add("고기류");

    List<Category> categories = new ArrayList<>();
    categories.add(new Category("고기류"));

    //when
    when(categoryRepository.findAllByNameIn(categoryNames)).thenReturn(categories);

    //then
    assertThrows(DuplicateCategoryException.class,
            () -> categoryService.addMainCategories(new CategoryParam(categoryNames)));
  }

  @Test
  @DisplayName("메인 카테고리가 존재하지 않으면 NotExistCategoryException이 발생합니다.")
  public void givenWrongParentIdWhenFindCategoryByIdThrowsException() {

    //TODO 테스트 추가
  }

  @Test
  @DisplayName("서브 카테고리 이름이 중복되면 DuplicateCategoryException이 발생합니다.")
  public void givenDuplicatedSubCategoryNameThrowsException() {

    //TODO 테스트 추가
  }

  @Test
  @DisplayName("정상적인 카테고리 이름들이 입력되면 메인 카테고리 추가가 성공합니다.")
  public void givenRightCategoryParamAddMainCategoriesPassed() {

    //TODO 테스트 추가
  }

  @Test
  @DisplayName("정상적인 부모 카테고리 id와 카테고리 이름들이 입력되면 서브 카테고리 추가가 성공합니다.")
  public void givenRightParentCategoryIdAndCategoryParamAddSubCategoriesPassed() {

    //TODO 테스트 추가
  }
}
