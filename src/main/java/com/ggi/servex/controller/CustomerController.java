package com.ggi.servex.controller;

import com.ggi.servex.dto.CustomerDto;
import com.ggi.servex.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDto create(@RequestBody CustomerDto customerDto) {
        return customerService.create(customerDto);
    }

    @GetMapping("/{id}")
    public CustomerDto get(@PathVariable("id") String customerId) {
        return customerService.getById(customerId);
    }

    @GetMapping
    public List<CustomerDto> getAll() {
        return customerService.getAll();
    }

    @PutMapping("/{id}")
    public CustomerDto update(@PathVariable("id") String customerId, @RequestBody CustomerDto customerDto) {
        return customerService.update(customerId, customerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String customerId) {
        customerService.delete(customerId);
    }
}
