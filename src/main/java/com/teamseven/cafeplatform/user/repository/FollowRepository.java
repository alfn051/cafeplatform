package com.teamseven.cafeplatform.user.repository;

import com.teamseven.cafeplatform.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}