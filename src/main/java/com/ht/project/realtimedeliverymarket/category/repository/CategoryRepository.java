package com.ht.project.realtimedeliverymarket.category.repository;

import com.ht.project.realtimedeliverymarket.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  Optional<Category> findByName(@Param("categories_name") String name);
}
