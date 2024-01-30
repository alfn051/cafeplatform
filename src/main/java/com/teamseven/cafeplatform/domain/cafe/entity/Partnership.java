package com.teamseven.cafeplatform.domain.cafe.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partnership")
public class Partnership extends BaseEntity {
    @Column(nullable = true)
    private LocalDate startDate;
    @Column(nullable = true)
    private LocalDate endDate;
    @Column(nullable = true)
    private Boolean freeTrial;
    @Column(nullable = true)
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

}