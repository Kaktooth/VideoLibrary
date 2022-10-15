package com.projects.videolibrary.model;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "videos")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoData extends Domain {

  String videoId;
  UUID categoryId;

  @Builder
  public VideoData(UUID id, String videoId, UUID categoryId) {
    super(id);
    this.videoId = videoId;
    this.categoryId = categoryId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    VideoData videoData = (VideoData) o;
    return getId() != null && Objects.equals(getId(), videoData.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
