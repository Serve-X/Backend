package com.ggi.servex.dto;

import com.ggi.servex.entity.CustomerEntity;
import com.ggi.servex.entity.OderDetailEntity;
import com.ggi.servex.enums.OderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String orderId;
    private OderStatus status;
    private int tableNumber;

    private CustomerDto customer;
    private List<OderDetailEntity> oderDetails;
}
