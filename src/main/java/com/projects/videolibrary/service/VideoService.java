package com.projects.videolibrary.service;

import com.projects.videolibrary.model.Category;
import com.projects.videolibrary.model.VideoData;
import com.projects.videolibrary.model.VideoDataRequest;
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

  public VideoData saveVideoData(VideoDataRequest videoDataRequest) {
    UUID id = videoDataRequest.id();
    if (id == null) {
      id = UUID.randomUUID();
    }

    VideoData videoData = VideoData.builder()
        .id(id)
        .videoId(videoDataRequest.videoId())
        .categoryId(videoDataRequest.categoryId())
        .build();

    return videoRepository.save(videoData);
  }

  public void deleteVideoData(UUID deleteId) {
    videoRepository.deleteById(deleteId);
  }

  public List<VideoData> getVideoDataByCategoryId(UUID categoryId) {
    return videoRepository.findAllByCategoryId(categoryId);
  }

  public Map<Category, List<VideoData>> getAllVideoDataWithCategories() {
    List<Category> categoryList = categoryRepository.findAll();
    Map<Category,  List<VideoData>> categoryVideoDataMap = new HashMap<>();
    for (var category : categoryList) {
      categoryVideoDataMap.put(category, getVideoDataByCategoryId(category.getId()));
    }
    return categoryVideoDataMap;
  }
}
