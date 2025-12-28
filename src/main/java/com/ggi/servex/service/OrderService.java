package com.ggi.servex.service;

import com.ggi.servex.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto create(OrderDto order);

    OrderDto update(String orderId, OrderDto order);

    OrderDto getById(String orderId);

    List<OrderDto> getAll();

    void delete(String orderId);
}
