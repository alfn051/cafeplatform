package com.teamseven.cafeplatform.domain.order.entity;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.order.common.OrderState;
import com.teamseven.cafeplatform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @Column(nullable = true)
    private Long totalPrice;
    @Column(nullable = true)
    private Long pointDiscount;
    @Column(nullable = true)
    private Long stampDiscount;
    @Column(nullable = true)
    private Long amount;
    private LocalDateTime orderedTime;

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

    @OneToOne(mappedBy = "order", orphanRemoval = true)
    private Review review;

}