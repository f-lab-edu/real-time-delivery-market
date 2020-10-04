package com.ht.project.realtimedeliverymarket.category.controller;

import com.ht.project.realtimedeliverymarket.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @PostMapping
  public HttpStatus addMainCategory (@RequestBody String name) {

    categoryService.addMainCategory(name);
    return HttpStatus.OK;
  }

  @PostMapping("/{id}")
  public HttpStatus addSubCategory (@RequestBody String name, @PathVariable Long id) {

    categoryService.addSubCategory(id, name);
    return HttpStatus.OK;
  }
}
