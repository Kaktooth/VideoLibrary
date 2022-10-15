package com.projects.videolibrary.repository;

import com.projects.videolibrary.model.Domain;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface CommonRepository<E extends Domain> extends JpaRepository<E, UUID>,
    PagingAndSortingRepository<E, UUID>,
    JpaSpecificationExecutor<E> {

}
