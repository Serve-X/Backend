package com.ggi.servex.service;

import com.ggi.servex.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    CustomerDto create(CustomerDto customer);

    CustomerDto update(String customerId, CustomerDto customer);

    CustomerDto getById(String customerId);

    List<CustomerDto> getAll();

    void delete(String customerId);
}
