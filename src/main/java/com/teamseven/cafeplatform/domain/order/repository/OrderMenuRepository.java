package com.teamseven.cafeplatform.domain.order.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.order.entity.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    public Optional<OrderMenu> findByOrderAndMenu(Order order, Menu menu);
}