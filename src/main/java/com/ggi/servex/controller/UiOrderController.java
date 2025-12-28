package com.ggi.servex.controller;

import com.ggi.servex.dto.ui.OrderView;
import com.ggi.servex.dto.ui.PlaceOrderRequest;
import com.ggi.servex.dto.ui.StatusUpdateRequest;
import com.ggi.servex.enums.OderStatus;
import com.ggi.servex.service.OrderViewService;
import com.ggi.servex.service.OrderWorkflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/ui/orders")
@RequiredArgsConstructor
public class UiOrderController {

    private final OrderWorkflowService orderWorkflowService;
    private final OrderViewService orderViewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderView placeOrder(@Valid @RequestBody PlaceOrderRequest request) {
        return orderWorkflowService.placeOrder(request);
    }

    @GetMapping
    public List<OrderView> getOrders(@RequestParam(value = "tableNumber", required = false) Integer tableNumber) {
        if (tableNumber != null) {
            return orderViewService.getByTableNumber(tableNumber);
        }
        return orderViewService.getAll();
    }

    @GetMapping("/{orderId}")
    public OrderView getOrder(@PathVariable String orderId) {
        return orderViewService.getOne(orderId);
    }

    @PatchMapping("/{orderId}/status")
    public OrderView updateStatus(@PathVariable String orderId, @Valid @RequestBody StatusUpdateRequest request) {
        OderStatus newStatus = request.status();
        return orderWorkflowService.updateStatus(orderId, newStatus);
    }
}
