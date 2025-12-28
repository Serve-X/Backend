package com.ggi.servex.controller;

import com.ggi.servex.dto.ReviewDto;
import com.ggi.servex.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewDto> getAll(@RequestParam(value = "itemId", required = false) String itemId) {
        if (itemId != null && !itemId.isBlank()) {
            return reviewService.getByItem(itemId);
        }
        return reviewService.getAll();
    }

    @GetMapping("/{id}")
    public ReviewDto get(@PathVariable("id") String reviewId) {
        return reviewService.getById(reviewId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String reviewId) {
        reviewService.delete(reviewId);
    }
}
