package com.ggi.servex.service;

import com.ggi.servex.dto.ui.OrderLineView;
import com.ggi.servex.dto.ui.OrderView;
import com.ggi.servex.entity.OderDetailEntity;
import com.ggi.servex.entity.OrderEntity;
import com.ggi.servex.repository.OderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderViewService {

    private final OderRepository oderRepository;

    @Transactional(readOnly = true)
    public List<OrderView> getAll() {
        return oderRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toView)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderView> getByTableNumber(int tableNumber) {
        return oderRepository.findByTableNumberOrderByCreatedAtDesc(tableNumber).stream()
                .map(this::toView)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderView getOne(String orderId) {
        return toView(oderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + orderId)));
    }

    public OrderView toView(OrderEntity entity) {
        return new OrderView(
                entity.getOrderId(),
                entity.getTableNumber(),
                entity.getStatus(),
                entity.getCustomer() != null ? entity.getCustomer().getCustomerName() : null,
                entity.getCustomer() != null ? entity.getCustomer().getCustomerEmail() : null,
                entity.getCustomer() != null ? entity.getCustomer().getCustomerPhone() : null,
                mapLines(entity.getOderDetails()),
                entity.getCreatedAt()
        );
    }

    private List<OrderLineView> mapLines(List<OderDetailEntity> details) {
        if (details == null || details.isEmpty()) {
            return Collections.emptyList();
        }
        return details.stream()
                .map(detail -> new OrderLineView(
                        detail.getItem() != null ? detail.getItem().getItemId() : null,
                        detail.getItem() != null ? detail.getItem().getItemName() : null,
                        detail.getQty()
                ))
                .toList();
    }
}
