package com.ggi.servex.service;

import com.ggi.servex.dto.ReviewDto;
import com.ggi.servex.dto.ui.ReviewRequest;

import java.util.List;

public interface ReviewService {
    ReviewDto create(ReviewRequest request);

    ReviewDto getById(String reviewId);

    List<ReviewDto> getAll();

    List<ReviewDto> getByItem(String itemId);

    void delete(String reviewId);
}
