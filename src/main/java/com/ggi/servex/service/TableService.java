package com.ggi.servex.service;

import com.ggi.servex.dto.TableDto;

import java.util.List;

public interface TableService {
    TableDto create(TableDto table);

    TableDto update(String tableId, TableDto table);

    TableDto getById(String tableId);

    List<TableDto> getAll();

    void delete(String tableId);
}
