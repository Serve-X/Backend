package com.ggi.servex.dto;

import com.ggi.servex.entity.TableEntity;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {
    private String restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private Boolean isOpen;

    private List<TableEntity> table;
}
