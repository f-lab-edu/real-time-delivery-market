package com.ht.project.realtimedeliverymarket.category.service;

import com.ht.project.realtimedeliverymarket.category.exception.DuplicateCategoryException;
import com.ht.project.realtimedeliverymarket.category.exception.NotExistCategoryException;
import com.ht.project.realtimedeliverymarket.category.model.dto.CategoryParam;
import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import com.ht.project.realtimedeliverymarket.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  /*
  Entity 설계 시, N 쪽에 외래키를 위치시켰기 때문에
  데이터를 조작할 때에도 N에 해당하는 child Category 데이터를 조작해야만 값이 적용됩니다.
   */
  
  @Transactional
  public void addMainCategories(CategoryParam categoryParam) {

    categoryRepository.saveAll(createNewCategories(categoryParam.getCategoryNames()));
  }
  
  @Transactional
  public void addSubCategories(Long categoryId, CategoryParam categoryParam) {

    categoryRepository.saveAll(createNewCategories(categoryId, categoryParam.getCategoryNames()));
  }

  private List<Category> createNewCategories(List<String> categoryNames) {

    isDuplicatedCategoryName(categoryNames);
    List<Category> newCategories = new ArrayList<>();
    categoryNames.forEach(c -> newCategories.add(new Category(c)));

    return newCategories;
  }

  private List<Category> createNewCategories(Long categoryId, List<String> categoryNames) {
  
    Category parentCategory =
            categoryRepository.findById(categoryId)
                    .orElseThrow(
                            () -> new NotExistCategoryException("부모 카테고리가 존재하지 않습니다."));

    isDuplicatedCategoryName(categoryNames);
    List<Category> newCategories = new ArrayList<>();
    categoryNames.forEach(c -> {

      Category newCategory = new Category(c);
      newCategory.setParent(parentCategory);
      newCategories.add(newCategory);
    });

    return newCategories;
  }

  private void isDuplicatedCategoryName(List<String> categoryNames) {

    if (!categoryRepository.findAllByNameIn(categoryNames).isEmpty()) {

      throw new DuplicateCategoryException("중복된 카테고리가 존재합니다.");
    }
  }
}
