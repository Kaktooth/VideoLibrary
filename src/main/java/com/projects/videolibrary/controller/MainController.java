package com.projects.videolibrary.controller;

import com.projects.videolibrary.model.Category;
import com.projects.videolibrary.model.VideoData;
import com.projects.videolibrary.service.CategoryService;
import com.projects.videolibrary.service.VideoService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/main")
public class MainController {

  private final CategoryService categoryService;
  private final VideoService videoService;

  @GetMapping
  public String getMainPage(Model model) {
    List<Category> categoryList = categoryService.getAllCategories();
    model.addAttribute("categoryList", categoryList);
    if (model.getAttribute("category") == null) {
      Map<Category, List<VideoData>> videoDataByCategory =
          videoService.getAllVideoDataWithCategories();
      model.addAttribute("videoDataByCategory", videoDataByCategory);
    } else {
      Category category = categoryService.getCategoryByName(
          (String) model.getAttribute("category"));
      if (category != null) {
        List<VideoData> videoData = videoService.getVideoDataByCategoryId(category.getId());
        model.addAttribute("videoData", videoData);
      }
    }
    return "main";
  }
}
