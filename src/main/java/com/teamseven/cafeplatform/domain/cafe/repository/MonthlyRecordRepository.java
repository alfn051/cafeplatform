package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.MonthlyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyRecordRepository extends JpaRepository<MonthlyRecord, Long> {
    public Optional<MonthlyRecord> findFirstByCafeIdOrderByMonthDesc(Long cafeId);
}