package com.ggi.servex.service;

import com.ggi.servex.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto item);

    ItemDto update(String itemId, ItemDto item);

    ItemDto getById(String itemId);

    List<ItemDto> getAll();

    void delete(String itemId);
}
