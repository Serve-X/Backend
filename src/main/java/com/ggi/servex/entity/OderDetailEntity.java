package com.ggi.servex.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "oder_detail")
public class OderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String oderDetailId;
    private  int qty;

    @ManyToOne()
    @JsonBackReference(value = "order-details") // Fix: reference name must match order side
    @JoinColumn(name = "fk_oder_id")
    private OrderEntity order;

    @ManyToOne()
    @JsonBackReference(value = "item-details") // Fix: separate reference for item relationship
    @JoinColumn(name = "fk_item_id")
    private ItemEntity item;
}
