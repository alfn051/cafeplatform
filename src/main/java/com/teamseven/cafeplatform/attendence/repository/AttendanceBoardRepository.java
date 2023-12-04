package com.teamseven.cafeplatform.attendence.repository;

import com.teamseven.cafeplatform.attendence.entity.AttendanceBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceBoardRepository extends JpaRepository<AttendanceBoard, Long> {
}