package com.projects.videolibrary.controller;

import com.projects.videolibrary.model.Category;
import com.projects.videolibrary.service.CategoryService;
import com.projects.videolibrary.service.VideoService;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
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

  @PostMapping("/add-video-with-category")
  public String addVideosWithCategory(@RequestParam("newCategory") String newCategory,
      @RequestParam("selectedCategory") String selectedCategory)
      throws IOException, InterruptedException {
    String searchCategory = URLEncoder.encode(newCategory, StandardCharsets.UTF_8.toString());
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(
            "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q="
                + searchCategory + "&key=AIzaSyBGVE76QAuWoX9rJaAOXeU_z4jz63KZC9g"))
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();
    HttpResponse<String> response = HttpClient.newHttpClient()
        .send(request, HttpResponse.BodyHandlers.ofString());
    UUID categoryId;
    if (selectedCategory == "") {
      Category category = categoryService.addCategory(newCategory);
      categoryId = category.getId();

    } else {
      Category category = categoryService.getCategoryByName(selectedCategory);
      categoryId = category.getId();
    }
    JSONObject obj = new JSONObject(response.body());
    JSONArray items = obj.getJSONArray("items");
    for (var item : items) {
      JSONObject id = ((JSONObject) item).getJSONObject("id");
      if (id.has("videoId")) {
        String idString = id.getString("videoId");
        videoService.saveVideoData(idString, categoryId);
      }
    }

    return "redirect:/admin-dashboard";
  }
}