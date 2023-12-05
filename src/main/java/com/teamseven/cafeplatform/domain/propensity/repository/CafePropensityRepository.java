package com.teamseven.cafeplatform.domain.propensity.repository;

import com.teamseven.cafeplatform.domain.propensity.entity.CafePropensity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafePropensityRepository extends JpaRepository<CafePropensity, Long> {
}