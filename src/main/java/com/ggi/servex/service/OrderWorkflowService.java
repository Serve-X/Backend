package com.ggi.servex.service;

import com.ggi.servex.dto.ui.OrderView;
import com.ggi.servex.dto.ui.PlaceOrderRequest;
import com.ggi.servex.entity.CustomerEntity;
import com.ggi.servex.entity.ItemEntity;
import com.ggi.servex.entity.OderDetailEntity;
import com.ggi.servex.entity.OrderEntity;
import com.ggi.servex.entity.TableEntity;
import com.ggi.servex.enums.OderStatus;
import com.ggi.servex.repository.CustomerRepository;
import com.ggi.servex.repository.ItemRepository;
import com.ggi.servex.repository.OderRepository;
import com.ggi.servex.repository.TableRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderWorkflowService {

    private final OderRepository oderRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final TableRepository tableRepository;
    private final TableService tableService;
    private final OrderViewService orderViewService;
    private final UiEventService uiEventService;

    public OrderView placeOrder(PlaceOrderRequest request) {
        TableEntity table = tableRepository.findByTableNumber(request.tableNumber())
                .orElseThrow(() -> new EntityNotFoundException("Unknown table: " + request.tableNumber()));

        if (!table.isAvailable()) {
            throw new IllegalStateException("Table " + request.tableNumber() + " is not available.");
        }

        CustomerEntity customer = resolveCustomer(request);
        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setStatus(OderStatus.PENDING);
        order.setTableNumber(table.getTableNumber());

        List<OderDetailEntity> detailEntities = new ArrayList<>();
        for (PlaceOrderRequest.LineItem itemRequest : request.items()) {
            ItemEntity item = itemRepository.findById(itemRequest.itemId())
                    .orElseThrow(() -> new EntityNotFoundException("Item not found: " + itemRequest.itemId()));
            OderDetailEntity detail = new OderDetailEntity();
            detail.setItem(item);
            detail.setQty(itemRequest.quantity());
            detail.setOrder(order);
            detailEntities.add(detail);
        }
        order.setOderDetails(detailEntities);

        OrderEntity saved = oderRepository.save(order);

        table.setAvailable(false);
        tableRepository.save(table);

        broadcast();

        return orderViewService.toView(saved);
    }

    public OrderView updateStatus(String orderId, OderStatus status) {
        OrderEntity order = oderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId));
        order.setStatus(status);
        OrderEntity saved = oderRepository.save(order);
        syncTableAvailability(saved);
        broadcast();
        return orderViewService.toView(saved);
    }

    private CustomerEntity resolveCustomer(PlaceOrderRequest request) {
        return customerRepository.findByCustomerEmail(request.customer().email())
                .map(existing -> updateCustomer(existing, request))
                .orElseGet(() -> createCustomer(request));
    }

    private CustomerEntity updateCustomer(CustomerEntity existing, PlaceOrderRequest request) {
        existing.setCustomerName(request.customer().name());
        existing.setCustomerPhone(request.customer().phone());
        return customerRepository.save(existing);
    }

    private CustomerEntity createCustomer(PlaceOrderRequest request) {
        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerName(request.customer().name());
        entity.setCustomerEmail(request.customer().email());
        entity.setCustomerPhone(request.customer().phone());
        return customerRepository.save(entity);
    }

    private void syncTableAvailability(OrderEntity order) {
        tableRepository.findByTableNumber(order.getTableNumber())
                .ifPresent(table -> {
                    boolean shouldBeAvailable = order.getStatus() == OderStatus.COMPLETED
                            || order.getStatus() == OderStatus.REJECTED;
                    table.setAvailable(shouldBeAvailable);
                    tableRepository.save(table);
                });
    }

    private void broadcast() {
        uiEventService.sendOrders(orderViewService.getAll());
        uiEventService.sendTables(tableService.getAll());
    }
}
