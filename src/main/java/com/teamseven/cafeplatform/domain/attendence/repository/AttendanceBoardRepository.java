package com.teamseven.cafeplatform.domain.attendence.repository;

import com.teamseven.cafeplatform.domain.attendence.entity.AttendanceBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceBoardRepository extends JpaRepository<AttendanceBoard, Long> {
}