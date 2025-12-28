package com.ggi.servex.service.impl;

import com.ggi.servex.dto.TableDto;
import com.ggi.servex.entity.TableEntity;
import com.ggi.servex.repository.TableRepository;
import com.ggi.servex.service.TableService;
import com.ggi.servex.service.UiEventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository repository;
    private final ObjectMapper objectMapper;
    private final UiEventService uiEventService;

    @Override
    public TableDto create(TableDto table) {
        TableEntity entity = toEntity(table);
        entity.setTableId(null);
        TableDto saved = toDto(repository.save(entity));
        broadcastTables();
        return saved;
    }

    @Override
    public TableDto update(String tableId, TableDto table) {
        TableEntity existing = getEntity(tableId);
        TableEntity toSave = toEntity(table);
        toSave.setTableId(existing.getTableId());
        TableDto updated = toDto(repository.save(toSave));
        broadcastTables();
        return updated;
    }

    @Override
    public TableDto getById(String tableId) {
        return toDto(getEntity(tableId));
    }

    @Override
    public List<TableDto> getAll() {
        return loadAll();
    }

    @Override
    public void delete(String tableId) {
        repository.delete(getEntity(tableId));
        broadcastTables();
    }

    private List<TableDto> loadAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private void broadcastTables() {
        uiEventService.sendTables(loadAll());
    }

    private TableEntity getEntity(String tableId) {
        return repository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Table not found: " + tableId));
    }

    private TableDto toDto(TableEntity entity) {
        return objectMapper.convertValue(entity, TableDto.class);
    }

    private TableEntity toEntity(TableDto dto) {
        return objectMapper.convertValue(dto, TableEntity.class);
    }
}
