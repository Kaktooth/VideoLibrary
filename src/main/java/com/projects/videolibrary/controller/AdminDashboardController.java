package com.projects.videolibrary.controller;

import com.projects.videolibrary.model.Category;
import com.projects.videolibrary.service.CategoryService;
import com.projects.videolibrary.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin-dashboard")
@AllArgsConstructor
public class AdminDashboardController {

  private final CategoryService categoryService;
  private final VideoService videoService;

  @GetMapping
  public String getAdminPage(Model model) {
    model.addAttribute("categoryList", categoryService.getAllCategories());
    return "admin-dashboard";
  }

  @PostMapping("/add-category")
  public String addCategory(@RequestParam("category") String category) {
    categoryService.addCategory(category);
    return "redirect:/admin-dashboard";
  }

  @PostMapping("/remove-category")
  public String removeCategory(@RequestParam("category") String category) {
    categoryService.removeCategory(category);
    return "redirect:/admin-dashboard";
  }

  @PostMapping("/add-video")
  public String addVideo(@RequestParam("videoId") String videoId,
      @RequestParam("categorySelect") String categorySelect) {
    Category category = categoryService.getCategoryByName(categorySelect);
    videoService.saveVideoData(videoId, category.getId());
    return "redirect:/admin-dashboard";
  }

  @PostMapping("/remove-video")
  public String removeVideo(@RequestParam("videoId") String videoId) {
    videoService.deleteVideoData(videoId);
    return "redirect:/admin-dashboard";
  }
}