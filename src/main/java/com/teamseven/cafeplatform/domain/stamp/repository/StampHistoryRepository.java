package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.stamp.entity.StampHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampHistoryRepository extends JpaRepository<StampHistory, Long> {
}