package com.ggi.servex.repository;

import com.ggi.servex.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findAllByOrderByCreatedAtDesc();

    List<ReviewEntity> findByItem_ItemIdOrderByCreatedAtDesc(String itemId);
}
