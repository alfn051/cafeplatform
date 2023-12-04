package com.teamseven.cafeplatform.user.repository;

import com.teamseven.cafeplatform.user.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}