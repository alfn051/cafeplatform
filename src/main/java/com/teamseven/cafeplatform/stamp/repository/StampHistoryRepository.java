package com.teamseven.cafeplatform.stamp.repository;

import com.teamseven.cafeplatform.stamp.entity.StampHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampHistoryRepository extends JpaRepository<StampHistory, Long> {
}