package com.teamseven.cafeplatform.domain.propensity.repository;

import com.teamseven.cafeplatform.domain.propensity.entity.UserPropensity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPropensityRepository extends JpaRepository<UserPropensity, Long> {
}