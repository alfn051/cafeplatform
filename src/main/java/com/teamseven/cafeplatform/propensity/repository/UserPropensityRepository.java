package com.teamseven.cafeplatform.propensity.repository;

import com.teamseven.cafeplatform.propensity.entity.UserPropensity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPropensityRepository extends JpaRepository<UserPropensity, Long> {
}