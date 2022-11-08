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
import org.springframework.web.bind.annotation.PathVariable;
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
    addCategoryList(model);
    Map<Category, List<VideoData>> videoDataByCategory =
        videoService.getAllVideoDataWithCategories();
    model.addAttribute("videoDataByCategory", videoDataByCategory);

    return "main";
  }

  @GetMapping("/{selectCategory}")
  public String getMainPage(@PathVariable String selectCategory, Model model) {
    addCategoryList(model);
    Category selectedCategory = categoryService.getCategoryByName(selectCategory);
    List<VideoData> selectedVideos = videoService.getVideoDataByCategoryId(
        selectedCategory.getId());
    model.addAttribute("selectedCategory", selectedCategory);
    model.addAttribute("selectedVideos", selectedVideos);
    return "main";
  }

  @GetMapping("/player/{videoId}")
  public String getPlayer(@PathVariable String videoId, Model model) {
    addCategoryList(model);
    VideoData video = videoService.getVideoDataByVideoId(videoId);
    model.addAttribute("url", video.getYoutubeLink());
    return "main";
  }

  private void addCategoryList(Model model) {
    List<Category> categoryList = categoryService.getAllCategories();
    model.addAttribute("categoryList", categoryList);
  }
}
