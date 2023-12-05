package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampBoardRepository extends JpaRepository<StampBoard, Long> {
}