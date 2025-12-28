package com.ggi.servex.controller;

import com.ggi.servex.dto.ReviewDto;
import com.ggi.servex.dto.ui.ReviewRequest;
import com.ggi.servex.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ui/reviews")
@RequiredArgsConstructor
public class UiReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewDto create(@Valid @RequestBody ReviewRequest request) {
        return reviewService.create(request);
    }

    @GetMapping
    public List<ReviewDto> getAll(@RequestParam(value = "itemId", required = false) String itemId) {
        if (itemId != null && !itemId.isBlank()) {
            return reviewService.getByItem(itemId);
        }
        return reviewService.getAll();
    }
}
