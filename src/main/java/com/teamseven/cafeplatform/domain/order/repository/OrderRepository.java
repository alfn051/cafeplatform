package com.teamseven.cafeplatform.domain.order.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.order.common.OrderState;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findAllByUser(User user);

    public List<Order> findAllByUserAndCafe(User user, Cafe cafe);

    public Optional<Order> findByUserIdAndCafeIdAndOrderState(Long userId, Long cafeId, OrderState orderState);

}