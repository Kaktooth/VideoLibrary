package com.projects.videolibrary.model;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;

@Data
@RequiredArgsConstructor
@Entity
@AllArgsConstructor
@Table(name = "categories")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends Domain {

  String name;

  @Builder
  public Category(UUID id, String name) {
    super(id);
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Category categoryObject = (Category) o;
    return getId() != null && Objects.equals(getId(), categoryObject.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
