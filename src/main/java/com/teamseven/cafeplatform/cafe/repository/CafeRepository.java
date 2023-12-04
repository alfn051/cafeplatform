package com.teamseven.cafeplatform.cafe.repository;

import com.teamseven.cafeplatform.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
}