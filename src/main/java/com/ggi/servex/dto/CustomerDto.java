package com.ggi.servex.dto;

import com.ggi.servex.entity.OrderEntity;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDto {
    private String customerID;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    private List<OrderEntity> orders;
}
