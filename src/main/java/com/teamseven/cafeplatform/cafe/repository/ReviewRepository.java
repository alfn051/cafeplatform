package com.teamseven.cafeplatform.cafe.repository;

import com.teamseven.cafeplatform.cafe.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}