package com.ggi.servex.service.impl;

import tools.jackson.databind.ObjectMapper;
import com.ggi.servex.dto.CustomerDto;
import com.ggi.servex.dto.OrderDto;
import com.ggi.servex.entity.CustomerEntity;
import com.ggi.servex.entity.OrderEntity;
import com.ggi.servex.enums.OderStatus;
import com.ggi.servex.repository.OderRepository;
import com.ggi.servex.repository.TableRepository;
import com.ggi.servex.service.OrderService;
import com.ggi.servex.service.OrderViewService;
import com.ggi.servex.service.TableService;
import com.ggi.servex.service.UiEventService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional // keep lazy relations (customer) loaded until DTO conversion
public class OrderServiceImpl implements OrderService {

    private final OderRepository oderRepository;
    private final ObjectMapper objectMapper;
    private final TableRepository tableRepository;
    private final TableService tableService;
    private final OrderViewService orderViewService;
    private final UiEventService uiEventService;

    @Override
    public OrderDto create(OrderDto order) {
        OrderEntity entity = toEntity(order);
        entity.setOrderId(null);
        attachOrder(entity);
        OrderEntity saved = oderRepository.save(entity);
        syncTableAvailability(saved);
        publishSnapshots();
        return toDto(saved);
    }

    @Override
    public OrderDto update(String orderId, OrderDto order) {
        OrderEntity orderEntity = oderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        orderEntity.setStatus(order.getStatus());
        oderRepository.save(orderEntity);

//        OrderEntity existing = getEntity(orderId);
//        applyMutableFields(existing, order);
//        OrderEntity saved = oderRepository.save(existing);
//        syncTableAvailability(saved);
//        publishSnapshots();
        return toDto(orderEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getById(String orderId) {
        return toDto(getEntity(orderId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAll() {
        return oderRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String orderId) {
        OrderEntity entity = getEntity(orderId);
        oderRepository.delete(entity);
        releaseTable(entity);
        publishSnapshots();
    }

    private OrderEntity getEntity(String orderId) {
        return oderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
    }

    private OrderDto toDto(OrderEntity entity) {
        OrderDto dto = objectMapper.convertValue(entity, OrderDto.class);
        if (entity.getCustomer() != null) {
            // convert the associated customer explicitly; @JsonBackReference prevents automatic mapping
            CustomerDto customerDto = objectMapper.convertValue(entity.getCustomer(), CustomerDto.class);
            customerDto.setOrders(null);
            dto.setCustomer(customerDto);
        }
        return dto;
    }

    private OrderEntity toEntity(OrderDto dto) {
        return objectMapper.convertValue(dto, OrderEntity.class);
    }

    private void applyMutableFields(OrderEntity entity, OrderDto dto) {
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getTableNumber() != 0) {
            entity.setTableNumber(dto.getTableNumber());
        }
        if (dto.getCustomer() != null) {
            entity.setCustomer(objectMapper.convertValue(dto.getCustomer(), CustomerEntity.class));
        }
    }

    private void attachOrder(OrderEntity entity) {
        if (entity.getOderDetails() != null) {
            entity.getOderDetails().forEach(detail -> detail.setOrder(entity));
        }
    }

    private void syncTableAvailability(OrderEntity order) {
        tableRepository.findByTableNumber(order.getTableNumber())
                .ifPresent(table -> {
                    boolean available = order.getStatus() == OderStatus.COMPLETED
                            || order.getStatus() == OderStatus.REJECTED;
                    table.setAvailable(available);
                    tableRepository.save(table);
                });
    }

    private void releaseTable(OrderEntity order) {
        tableRepository.findByTableNumber(order.getTableNumber())
                .ifPresent(table -> {
                    table.setAvailable(true);
                    tableRepository.save(table);
                });
    }

    private void publishSnapshots() {
        uiEventService.sendOrders(orderViewService.getAll());
        uiEventService.sendTables(tableService.getAll());
    }
}
