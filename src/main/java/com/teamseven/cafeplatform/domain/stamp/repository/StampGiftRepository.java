package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.stamp.common.BoardState;
import com.teamseven.cafeplatform.domain.stamp.entity.StampGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StampGiftRepository extends JpaRepository<StampGift, Long> {
    List<StampGift> findAllByStampBoardCafeMemberUserIdAndUsed(Long userId, Boolean used);

    @Query("SELECT sg FROM StampGift sg WHERE sg.stampBoard.cafeMember.user.id = :userId AND sg.stampBoard.cafeMember.cafe.id = :cafeId AND sg.used = false ")
    List<StampGift> findAllByUserAndCafe(@Param("userId") Long userId, @Param("cafeId") Long cafeId);
}