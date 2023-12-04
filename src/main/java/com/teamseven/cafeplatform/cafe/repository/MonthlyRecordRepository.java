package com.teamseven.cafeplatform.cafe.repository;

import com.teamseven.cafeplatform.cafe.entity.MonthlyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyRecordRepository extends JpaRepository<MonthlyRecord, Long> {
}