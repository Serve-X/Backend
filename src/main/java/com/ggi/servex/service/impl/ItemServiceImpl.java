package com.ggi.servex.service.impl;

import tools.jackson.databind.ObjectMapper;
import com.ggi.servex.dto.ItemDto;
import com.ggi.servex.entity.ItemEntity;
import com.ggi.servex.repository.ItemRepository;
import com.ggi.servex.service.ItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ObjectMapper objectMapper;

    @Override
    public ItemDto create(ItemDto item) {
        ItemEntity entity = toEntity(item);
        entity.setItemId(null);
        return toDto(itemRepository.save(entity));
    }

    @Override
    public ItemDto update(String itemId, ItemDto item) {
        ItemEntity existing = getEntity(itemId);
        ItemEntity toSave = toEntity(item);
        toSave.setItemId(existing.getItemId());
        return toDto(itemRepository.save(toSave));
    }

    @Override
    public ItemDto getById(String itemId) {
        return toDto(getEntity(itemId));
    }

    @Override
    public List<ItemDto> getAll() {
        return itemRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String itemId) {
        itemRepository.delete(getEntity(itemId));
    }

    private ItemEntity getEntity(String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemId));
    }

    private ItemDto toDto(ItemEntity entity) {
        return objectMapper.convertValue(entity, ItemDto.class);
    }

    private ItemEntity toEntity(ItemDto dto) {
        return objectMapper.convertValue(dto, ItemEntity.class);
    }
}
