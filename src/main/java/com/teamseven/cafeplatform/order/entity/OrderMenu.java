package com.teamseven.cafeplatform.order.entity;

import com.teamseven.cafeplatform.cafe.entity.Menu;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.stamp.entity.StampGift;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_menu")
public class OrderMenu{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int quantity;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "stamp_gift_id")
    private StampGift stampGift;

}