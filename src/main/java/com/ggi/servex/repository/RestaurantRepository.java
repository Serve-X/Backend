package com.ggi.servex.repository;

import com.ggi.servex.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RestaurantRepository extends JpaRepository<RestaurantEntity, String> {
}
