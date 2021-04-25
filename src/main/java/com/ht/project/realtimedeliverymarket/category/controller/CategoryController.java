package com.ht.project.realtimedeliverymarket.category.controller;

import com.ht.project.realtimedeliverymarket.category.model.dto.CategoryParam;
import com.ht.project.realtimedeliverymarket.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping("/")
  public HttpStatus addMainCategories (@RequestBody @Valid CategoryParam categoryParam) {

    categoryService.addMainCategories(categoryParam);
    return HttpStatus.CREATED;
  }
  
  @PostMapping("/{categoryId}")
  public HttpStatus addSubCategories (@PathVariable Long categoryId,
                                      @RequestBody @Valid CategoryParam categoryParam) {

    categoryService.addSubCategories(categoryId, categoryParam);
    return HttpStatus.CREATED;
  }
}
