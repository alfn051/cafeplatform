package com.teamseven.cafeplatform.domain.attendence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance_setting")
public class AttendanceSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDate month;
    private int totalDay;
    private int dayPoint;
    private int sevenPoint;
    private int monthPoint;
}