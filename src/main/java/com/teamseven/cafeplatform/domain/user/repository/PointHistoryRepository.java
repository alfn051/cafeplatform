package com.teamseven.cafeplatform.domain.user.repository;

import com.teamseven.cafeplatform.domain.user.entity.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
}