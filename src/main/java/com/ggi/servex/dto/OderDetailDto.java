package com.ggi.servex.dto;

import com.ggi.servex.entity.ItemEntity;
import com.ggi.servex.entity.OrderEntity;
import lombok.Data;

@Data
public class OderDetailDto {
    private String oderDetailId;
    private  int qty;

    private OrderEntity order;
    private ItemEntity item;
}
