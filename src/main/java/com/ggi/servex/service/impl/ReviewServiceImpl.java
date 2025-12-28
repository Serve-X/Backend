package com.ggi.servex.service.impl;

import com.ggi.servex.dto.ReviewDto;
import com.ggi.servex.dto.ui.ReviewRequest;
import com.ggi.servex.entity.ItemEntity;
import com.ggi.servex.entity.ReviewEntity;
import com.ggi.servex.repository.ItemRepository;
import com.ggi.servex.repository.ReviewRepository;
import com.ggi.servex.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService {
    private static final String DEFAULT_CATEGORY = "Suggestions";

    private final ReviewRepository reviewRepository;
    private final ItemRepository itemRepository;
    private final RestClient restClient = RestClient.create();

    @Value("${servex.review.categorizer-url:http://localhost:8001/categorize}")
    private String categorizerUrl;

    @Override
    public ReviewDto create(ReviewRequest request) {
        ItemEntity item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + request.itemId()));
        String text = request.text() != null ? request.text().trim() : "";

        ReviewEntity entity = new ReviewEntity();
        entity.setItem(item);
        entity.setRating(request.rating());
        entity.setText(text);
        entity.setCategory(resolveCategory(text));

        return toDto(reviewRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewDto getById(String reviewId) {
        return toDto(reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + reviewId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getAll() {
        return reviewRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto> getByItem(String itemId) {
        return reviewRepository.findByItem_ItemIdOrderByCreatedAtDesc(itemId).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void delete(String reviewId) {
        ReviewEntity entity = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + reviewId));
        reviewRepository.delete(entity);
    }

    private ReviewDto toDto(ReviewEntity entity) {
        ReviewDto dto = new ReviewDto();
        dto.setReviewId(entity.getReviewId());
        if (entity.getItem() != null) {
            dto.setItemId(entity.getItem().getItemId());
            dto.setItemName(entity.getItem().getItemName());
        }
        dto.setRating(entity.getRating());
        dto.setText(entity.getText());
        dto.setCategory(entity.getCategory());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

    private String resolveCategory(String text) {
        if (text == null || text.isBlank()) {
            return DEFAULT_CATEGORY;
        }
        try {
            CategorizerResponse response = restClient.post()
                    .uri(categorizerUrl)
                    .body(new CategorizerPayload(text))
                    .retrieve()
                    .body(CategorizerResponse.class);
            if (response != null && response.category() != null && !response.category().isBlank()) {
                return response.category();
            }
        } catch (Exception ignored) {
        }
        return DEFAULT_CATEGORY;
    }

    private record CategorizerPayload(String text) {
    }

    private record CategorizerResponse(String category) {
    }
}
