package com.teamseven.cafeplatform.domain.attendence.repository;

import com.teamseven.cafeplatform.domain.attendence.entity.AttendanceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceHistoryRepository extends JpaRepository<AttendanceHistory, Long> {
}