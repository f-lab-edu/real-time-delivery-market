package com.ht.project.realtimedeliverymarket.category.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryParam {

  List<@NotBlank(message = "카테고리명은 필수입니다.") String> categoryNames;
}
