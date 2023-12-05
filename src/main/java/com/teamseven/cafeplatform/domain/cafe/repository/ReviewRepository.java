package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}