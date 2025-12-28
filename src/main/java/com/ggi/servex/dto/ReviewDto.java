package com.ggi.servex.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class ReviewDto {
    private String reviewId;
    private String itemId;
    private String itemName;
    private Integer rating;
    private String text;
    private String category;
    private Instant createdAt;
}
