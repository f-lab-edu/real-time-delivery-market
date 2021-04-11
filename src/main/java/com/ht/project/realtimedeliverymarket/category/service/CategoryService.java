package com.ht.project.realtimedeliverymarket.category.service;

import com.ht.project.realtimedeliverymarket.category.exception.NotExistCategoryException;
import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import com.ht.project.realtimedeliverymarket.category.repository.CategoryRepository;
import com.ht.project.realtimedeliverymarket.category.exception.DuplicateCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public void addCategory(Long parentId, String name) {

    isDuplicatedCategoryName(name);
    Category newCategory = new Category(name);

    if (parentId != null) {
      newCategory.setParent(categoryRepository.findById(parentId)
              .orElseThrow(NotExistCategoryException::new));
    }

    categoryRepository.save(newCategory);
  }

  private void isDuplicatedCategoryName(String name) {

    categoryRepository.findByName(name)
            .ifPresent(c -> {throw new DuplicateCategoryException("중복된 카테고리 입니다.");});
  }
}
