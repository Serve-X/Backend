package com.ggi.servex.service;

import com.ggi.servex.dto.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    RestaurantDto create(RestaurantDto restaurant);

    RestaurantDto update(String restaurantId, RestaurantDto restaurant);

    RestaurantDto getById(String restaurantId);

    List<RestaurantDto> getAll();

    void delete(String restaurantId);
}
