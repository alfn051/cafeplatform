package com.teamseven.cafeplatform.domain.user.repository;

import com.teamseven.cafeplatform.domain.user.entity.CafeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeMemberRepository extends JpaRepository<CafeMember, Long> {
}