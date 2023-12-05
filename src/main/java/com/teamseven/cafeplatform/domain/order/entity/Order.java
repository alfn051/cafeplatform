package com.teamseven.cafeplatform.domain.order.entity;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.order.common.OrderState;
import com.teamseven.cafeplatform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Column(nullable = false)
    private long totalPrice;
    @Column(nullable = false)
    private long pointDiscount;
    @Column(nullable = false)
    private long stampDiscount;
    @Column(nullable = false)
    private long amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_state")
    private OrderState orderState;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

}