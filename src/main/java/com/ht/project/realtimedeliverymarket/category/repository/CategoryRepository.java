package com.ht.project.realtimedeliverymarket.category.repository;

import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findAllByNameIn(List<String> categoryNames);
}
