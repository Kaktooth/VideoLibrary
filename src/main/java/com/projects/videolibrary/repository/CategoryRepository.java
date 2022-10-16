package com.projects.videolibrary.repository;

import com.projects.videolibrary.model.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CommonRepository<Category> {

  Category getCategoryByName(String name);
}