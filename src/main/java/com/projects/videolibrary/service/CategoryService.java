package com.projects.videolibrary.service;

import com.projects.videolibrary.model.Category;
import com.projects.videolibrary.repository.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public Category getCategoryByName(String name) {
    return categoryRepository.getCategoryByName(name);
  }

  public Category addCategory(String name) {
    return categoryRepository.save(new Category(name));
  }

  public void removeCategory(String name) {
    categoryRepository.deleteByName(name);
  }
}