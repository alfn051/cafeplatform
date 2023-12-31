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
@Table(name = "monthly_record")
public class MonthlyRecord extends BaseEntity {
    private LocalDate month;
    private long amount;
    private long stampAmount;
    private long pointAmount;
    private long totalAmount;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

}