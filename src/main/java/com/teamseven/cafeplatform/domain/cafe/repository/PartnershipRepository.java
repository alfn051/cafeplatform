package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Partnership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    public Optional<Partnership> findFirstByCafeOrderByEndDateDesc(Cafe cafe);
}