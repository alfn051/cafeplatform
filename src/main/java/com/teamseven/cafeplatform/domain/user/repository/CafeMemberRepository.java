package com.teamseven.cafeplatform.domain.user.repository;

import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeMemberRepository extends JpaRepository<CafeMember, Long> {
    Optional<CafeMember> findByUserIdAndCafeId(Long userId, Long cafeId);
}