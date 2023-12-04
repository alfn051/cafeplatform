package com.teamseven.cafeplatform.attendence.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.user.entity.User;
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
@Table(name = "attendance_board")
public class AttendanceBoard extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "setting_id")
    private AttendanceSetting attendanceSetting;

    private boolean fullAttendance;

}