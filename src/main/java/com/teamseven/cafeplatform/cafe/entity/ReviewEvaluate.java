package com.teamseven.cafeplatform.cafe.entity;

import com.teamseven.cafeplatform.cafe.common.Evaluate;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "review_evaluate")
public class ReviewEvaluate extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Evaluate evaluate;

}