package com.ggi.servex.dto.ui;

import com.ggi.servex.enums.OderStatus;
import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull OderStatus status) {
}
