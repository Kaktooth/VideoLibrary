package com.projects.videolibrary.service;

import com.projects.videolibrary.model.VideoData;
import com.projects.videolibrary.model.VideoDataRequest;
import com.projects.videolibrary.repository.VideoRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VideoService {

  private final VideoRepository videoRepository;

  @Autowired
  public VideoService(VideoRepository videoRepository) {
    this.videoRepository = videoRepository;
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
}
