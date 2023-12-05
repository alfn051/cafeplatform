package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.MonthlyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonthlyRecordRepository extends JpaRepository<MonthlyRecord, Long> {
}