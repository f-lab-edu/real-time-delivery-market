package com.ht.project.realtimedeliverymarket.category.controller;

import com.ht.project.realtimedeliverymarket.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping(value = {"/", "/{id}"})
  public HttpStatus addCategory (@RequestBody String name,
                                 @PathVariable(required = false) Long id) {

    categoryService.addCategory(id, name);
    return HttpStatus.OK;
  }
}
