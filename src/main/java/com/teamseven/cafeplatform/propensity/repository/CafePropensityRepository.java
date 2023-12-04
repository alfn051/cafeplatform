package com.teamseven.cafeplatform.propensity.repository;

import com.teamseven.cafeplatform.propensity.entity.CafePropensity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafePropensityRepository extends JpaRepository<CafePropensity, Long> {
}