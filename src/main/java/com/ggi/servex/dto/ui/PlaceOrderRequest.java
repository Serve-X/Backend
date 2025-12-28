package com.ggi.servex.dto.ui;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Payload used by the customer-facing UI to place an order in a single call.
 */
public record PlaceOrderRequest(
        @Min(1) int tableNumber,
        @NotNull @Valid CustomerPayload customer,
        @NotEmpty List<@Valid LineItem> items
) {

    public record CustomerPayload(
            @NotBlank String name,
            @NotBlank @Email String email,
            @NotBlank String phone
    ) {
    }

    public record LineItem(
            @NotBlank String itemId,
            @Min(1) int quantity
    ) {
    }
}
