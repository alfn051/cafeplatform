package com.teamseven.cafeplatform.domain.user.repository;

import com.teamseven.cafeplatform.domain.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}