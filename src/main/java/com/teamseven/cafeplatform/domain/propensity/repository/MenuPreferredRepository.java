package com.teamseven.cafeplatform.domain.propensity.repository;

import com.teamseven.cafeplatform.domain.propensity.entity.MenuPreferred;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuPreferredRepository extends JpaRepository<MenuPreferred, Long> {
}