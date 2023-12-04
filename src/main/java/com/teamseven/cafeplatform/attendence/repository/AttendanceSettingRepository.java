package com.teamseven.cafeplatform.attendence.repository;

import com.teamseven.cafeplatform.attendence.entity.AttendanceSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceSettingRepository extends JpaRepository<AttendanceSetting, Long> {
}