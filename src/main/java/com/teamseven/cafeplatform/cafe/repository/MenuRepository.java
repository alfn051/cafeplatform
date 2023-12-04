package com.teamseven.cafeplatform.cafe.repository;

import com.teamseven.cafeplatform.cafe.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}