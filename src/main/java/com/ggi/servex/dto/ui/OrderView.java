package com.ggi.servex.dto.ui;

import com.ggi.servex.enums.OderStatus;

import java.time.Instant;
import java.util.List;

public record OrderView(
        String orderId,
        int tableNumber,
        OderStatus status,
        String customerName,
        String customerEmail,
        String customerPhone,
        List<OrderLineView> items,
        Instant createdAt
) {
}
