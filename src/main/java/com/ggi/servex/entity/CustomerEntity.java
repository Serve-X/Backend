package com.ggi.servex.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerID;
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    //FKs
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonManagedReference(value = "customer-orders") // Fix: explicit id avoids Jackson linking conflicts
    private List<OrderEntity> orders;
}
