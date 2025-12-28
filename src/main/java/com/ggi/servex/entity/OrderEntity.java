package com.ggi.servex.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ggi.servex.enums.OderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Oders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;
    private OderStatus status;
    private int tableNumber;
    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    //FKs
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_customer_id")
    @JsonBackReference(value = "customer-orders") // Fix: tie back reference to matching managed ref
    private CustomerEntity customer;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JsonManagedReference(value = "order-details") // Fix: unique id for detail relationship
    private List<OderDetailEntity> oderDetails;

}
