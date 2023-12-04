package com.teamseven.cafeplatform.stamp.repository;

import com.teamseven.cafeplatform.stamp.entity.StampBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampBoardRepository extends JpaRepository<StampBoard, Long> {
}