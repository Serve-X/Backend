package com.ggi.servex.controller;

import com.ggi.servex.dto.ItemDto;
import com.ggi.servex.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto create(@RequestBody ItemDto itemDto) {
        return itemService.create(itemDto);
    }

    @GetMapping("/{id}")
    public ItemDto get(@PathVariable("id") String itemId) {
        return itemService.getById(itemId);
    }

    @GetMapping
    public List<ItemDto> getAll() {
        return itemService.getAll();
    }

    @PutMapping("/{id}")
    public ItemDto update(@PathVariable("id") String itemId, @RequestBody ItemDto itemDto) {
        return itemService.update(itemId, itemDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String itemId) {
        itemService.delete(itemId);
    }
}
