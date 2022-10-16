package com.projects.videolibrary.repository;

import com.projects.videolibrary.model.VideoData;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends CommonRepository<VideoData> {

  List<VideoData> findAllByCategoryId(UUID categoryId);
}
