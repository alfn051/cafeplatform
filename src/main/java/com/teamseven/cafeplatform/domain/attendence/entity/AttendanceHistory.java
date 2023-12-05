package com.teamseven.cafeplatform.domain.attendence.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "attendance_history")
public class AttendanceHistory extends BaseEntity {
    private int earnPoint;
    private int consecutive;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private AttendanceBoard attendanceBoard;

}