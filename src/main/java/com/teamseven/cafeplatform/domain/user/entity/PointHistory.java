package com.teamseven.cafeplatform.domain.user.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "point_history")
public class PointHistory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private long usedAmount;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Order order;

}