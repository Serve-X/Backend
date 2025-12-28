package com.ggi.servex.service.impl;

import tools.jackson.databind.ObjectMapper;
import com.ggi.servex.dto.CustomerDto;
import com.ggi.servex.entity.CustomerEntity;
import com.ggi.servex.repository.CustomerRepository;
import com.ggi.servex.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    @Override
    public CustomerDto create(CustomerDto customer) {
        CustomerEntity entity = toEntity(customer);
        entity.setCustomerID(null);
        return toDto(customerRepository.save(entity));
    }

    @Override
    public CustomerDto update(String customerId, CustomerDto customer) {
        CustomerEntity existing = getEntity(customerId);
        CustomerEntity toSave = toEntity(customer);
        toSave.setCustomerID(existing.getCustomerID());
        return toDto(customerRepository.save(toSave));
    }

    @Override
    public CustomerDto getById(String customerId) {
        return toDto(getEntity(customerId));
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String customerId) {
        customerRepository.delete(getEntity(customerId));
    }

    private CustomerEntity getEntity(String customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));
    }

    private CustomerDto toDto(CustomerEntity entity) {
        return objectMapper.convertValue(entity, CustomerDto.class);
    }

    private CustomerEntity toEntity(CustomerDto dto) {
        return objectMapper.convertValue(dto, CustomerEntity.class);
    }
}
