package com.ggi.servex.controller;

import com.ggi.servex.dto.TableDto;
import com.ggi.servex.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TableDto create(@RequestBody TableDto tableDto) {
        return tableService.create(tableDto);
    }

    @GetMapping("/{id}")
    public TableDto get(@PathVariable("id") String tableId) {
        return tableService.getById(tableId);
    }

    @GetMapping
    public List<TableDto> getAll() {
        return tableService.getAll();
    }

    @PutMapping("/{id}")
    public TableDto update(@PathVariable("id") String tableId, @RequestBody TableDto tableDto) {
        return tableService.update(tableId, tableDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String tableId) {
        tableService.delete(tableId);
    }
}
