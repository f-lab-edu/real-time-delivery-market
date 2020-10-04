package com.ht.project.realtimedeliverymarket.category.service;

import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import com.ht.project.realtimedeliverymarket.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  public void addMainCategory(String name) {

    categoryRepository.findByName(name)
            .ifPresent(s -> {throw new IllegalArgumentException();});
    categoryRepository.save(new Category(name));
  }

  public void addSubCategory(Long id, String name) {

    categoryRepository.findByName(name).ifPresent(s -> {throw new IllegalArgumentException();});
    categoryRepository.save(new Category(name,
            categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new)));
  }
}
