package com.ggi.servex.repository;

import com.ggi.servex.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OderRepository extends JpaRepository<OrderEntity, String> {

    List<OrderEntity> findByTableNumberOrderByCreatedAtDesc(int tableNumber);

    List<OrderEntity> findAllByOrderByCreatedAtDesc();
}
