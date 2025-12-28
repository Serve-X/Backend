package com.ggi.servex.controller;

import com.ggi.servex.dto.RestaurantDto;
import com.ggi.servex.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantDto create(@RequestBody RestaurantDto restaurantDto) {
        return restaurantService.create(restaurantDto);
    }

    @GetMapping("/{id}")
    public RestaurantDto get(@PathVariable("id") String restaurantId) {
        return restaurantService.getById(restaurantId);
    }

    @GetMapping
    public List<RestaurantDto> getAll() {
        return restaurantService.getAll();
    }

    @PutMapping("/{id}")
    public RestaurantDto update(@PathVariable("id") String restaurantId, @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.update(restaurantId, restaurantDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String restaurantId) {
        restaurantService.delete(restaurantId);
    }
}
