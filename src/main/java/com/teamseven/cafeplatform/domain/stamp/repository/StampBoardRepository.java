package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampBoardRepository extends JpaRepository<StampBoard, Long> {
}