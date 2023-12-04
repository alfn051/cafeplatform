package com.teamseven.cafeplatform.user.repository;

import com.teamseven.cafeplatform.user.entity.CafeMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeMemberRepository extends JpaRepository<CafeMember, Long> {
}