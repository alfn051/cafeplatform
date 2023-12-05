package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}