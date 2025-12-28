package com.ggi.servex.dto;

import com.ggi.servex.entity.RestaurantEntity;
import lombok.Data;

@Data
public class TableDto {
    private String tableId;
    private int tableNumber;
    private int seatCount;
    private boolean isAvailable;

    private RestaurantEntity restaurant;
}
