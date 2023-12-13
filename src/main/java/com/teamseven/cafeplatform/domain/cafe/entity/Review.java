package com.teamseven.cafeplatform.domain.cafe.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.file.entity.Photo;
import com.teamseven.cafeplatform.file.entity.ReviewPhoto;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review extends BaseEntity {
    private int rating;
    private String title;
    private String content;
    private int evaluate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(mappedBy = "review", orphanRemoval = true)
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

}