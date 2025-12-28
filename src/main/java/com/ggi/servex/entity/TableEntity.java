package com.ggi.servex.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tables")
public class TableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tableId;
    private int tableNumber;
    private int seatCount;
    private boolean isAvailable;

    @ManyToOne()
    @JoinColumn(name = "fk_restaurant_id")
    @JsonBackReference(value = "restaurant-tables") // Fix: align name with RestaurantEntity reference
    private RestaurantEntity restaurant;
}
