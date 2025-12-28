package com.ggi.servex.service.impl;

import tools.jackson.databind.ObjectMapper;
import com.ggi.servex.dto.RestaurantDto;
import com.ggi.servex.entity.RestaurantEntity;
import com.ggi.servex.repository.RestaurantRepository;
import com.ggi.servex.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ObjectMapper objectMapper;

    @Override
    public RestaurantDto create(RestaurantDto restaurant) {
        RestaurantEntity entity = toEntity(restaurant);
        entity.setRestaurantId(null);
        return toDto(restaurantRepository.save(entity));
    }

    @Override
    public RestaurantDto update(String restaurantId, RestaurantDto restaurant) {
        RestaurantEntity existing = getEntity(restaurantId);
        RestaurantEntity toSave = toEntity(restaurant);
        toSave.setRestaurantId(existing.getRestaurantId());
        return toDto(restaurantRepository.save(toSave));
    }

    @Override
    public RestaurantDto getById(String restaurantId) {
        return toDto(getEntity(restaurantId));
    }

    @Override
    public List<RestaurantDto> getAll() {
        return restaurantRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String restaurantId) {
        restaurantRepository.delete(getEntity(restaurantId));
    }

    private RestaurantEntity getEntity(String restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found: " + restaurantId));
    }

    private RestaurantDto toDto(RestaurantEntity entity) {
        return objectMapper.convertValue(entity, RestaurantDto.class);
    }

    private RestaurantEntity toEntity(RestaurantDto dto) {
        return objectMapper.convertValue(dto, RestaurantEntity.class);
    }
}
