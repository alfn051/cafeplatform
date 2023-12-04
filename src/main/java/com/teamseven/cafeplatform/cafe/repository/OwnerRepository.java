package com.teamseven.cafeplatform.cafe.repository;

import com.teamseven.cafeplatform.cafe.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}