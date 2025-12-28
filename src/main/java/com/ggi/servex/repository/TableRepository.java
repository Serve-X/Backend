package com.ggi.servex.repository;

import com.ggi.servex.entity.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TableRepository extends JpaRepository<TableEntity, String> {

    Optional<TableEntity> findByTableNumber(int tableNumber);
}
