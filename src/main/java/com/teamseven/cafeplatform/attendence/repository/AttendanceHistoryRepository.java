package com.teamseven.cafeplatform.attendence.repository;

import com.teamseven.cafeplatform.attendence.entity.AttendanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceHistoryRepository extends JpaRepository<AttendanceHistory, Long> {
}