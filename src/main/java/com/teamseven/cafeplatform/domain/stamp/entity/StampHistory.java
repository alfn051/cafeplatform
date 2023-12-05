package com.teamseven.cafeplatform.domain.stamp.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stamp_history")
public class StampHistory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "stamp_board_id")
    private StampBoard stampBoard;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Order order;

    private boolean eventFirst;
    private boolean eventBirthday;
    private int accrualStamp;
}