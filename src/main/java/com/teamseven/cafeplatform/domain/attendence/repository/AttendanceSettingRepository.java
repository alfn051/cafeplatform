package com.teamseven.cafeplatform.domain.attendence.repository;

import com.teamseven.cafeplatform.domain.attendence.entity.AttendanceSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceSettingRepository extends JpaRepository<AttendanceSetting, Long> {
}