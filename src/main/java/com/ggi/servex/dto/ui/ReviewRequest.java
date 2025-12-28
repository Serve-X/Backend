package com.ggi.servex.dto.ui;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @NotBlank String itemId,
        @NotBlank String text,
        @Min(1) @Max(5) Integer rating
) {
}
