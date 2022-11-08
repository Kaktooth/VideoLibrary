package com.projects.videolibrary.service;

import com.projects.videolibrary.model.Category;
import com.projects.videolibrary.model.VideoData;
import com.projects.videolibrary.repository.CategoryRepository;
import com.projects.videolibrary.repository.VideoRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoService {

  private final VideoRepository videoRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public VideoService(VideoRepository videoRepository, CategoryRepository categoryRepository) {
    this.videoRepository = videoRepository;
    this.categoryRepository = categoryRepository;
  }

  public VideoData saveVideoData(String videoId, UUID categoryId) {
    VideoData videoData = VideoData.builder()
        .id(UUID.randomUUID())
        .videoId(videoId)
        .categoryId(categoryId)
        .build();

    return videoRepository.save(videoData);
  }

  public VideoData getVideoDataByVideoId(String id) {
    return videoRepository.findByVideoId(id);
  }

  public void deleteVideoData(String videoId) {
    videoRepository.deleteByVideoId(videoId);
  }

  public List<VideoData> getVideoDataByCategoryId(UUID categoryId) {
    return videoRepository.findAllByCategoryId(categoryId);
  }

  public Map<Category, List<VideoData>> getAllVideoDataWithCategories() {
    List<Category> categoryList = categoryRepository.findAll();
    Map<Category, List<VideoData>> categoryVideoDataMap = new HashMap<>();
    for (var category : categoryList) {
      categoryVideoDataMap.put(category, getVideoDataByCategoryId(category.getId()));
    }
    return categoryVideoDataMap;
  }
}
