package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.stamp.common.BoardState;
import com.teamseven.cafeplatform.domain.stamp.entity.StampBoard;
import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StampBoardRepository extends JpaRepository<StampBoard, Long> {
    public Optional<StampBoard> findByCafeMemberAndState(CafeMember cafeMember, BoardState state);

    public Optional<StampBoard> findFirstByCafeMemberOrderByCreateTimeDesc(CafeMember cafeMember);
    @Query("SELECT sb FROM StampBoard sb WHERE sb.cafeMember.user.id = :userId and sb.state = :state")
    public List<StampBoard> findAllByUserId(@Param("userId") Long userId, @Param("state")BoardState state);

    @Query("SELECT sb FROM StampBoard sb WHERE sb.cafeMember.user.id = :userId AND sb.cafeMember.cafe.id = :cafeId AND sb.state = :state")
    public Optional<StampBoard> findByUserIdAnAndCafeId(@Param("userId") Long userId, @Param("cafeId") Long cafeId, @Param("state") BoardState state);
}