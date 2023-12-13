package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.order.user IN" +
            "(SELECT f.followee FROM  Follow f WHERE f.follower.id = :userId)" +
            "ORDER BY r.createTime DESC ")
    public List<Review> findFolloweeRecentReview(@Param("userId") Long userId, Pageable pageable);
}