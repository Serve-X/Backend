package com.ggi.servex.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reviewId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_item_id")
    private ItemEntity item;

    private Integer rating;

    @Column(length = 2000)
    private String text;

    private String category;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;
}
