package com.teamseven.cafeplatform.domain.order.repository;

import com.teamseven.cafeplatform.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}