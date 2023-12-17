package com.teamseven.cafeplatform.domain.cafe.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "partnership")
public class Partnership extends BaseEntity {
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean freeTrial;
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

}