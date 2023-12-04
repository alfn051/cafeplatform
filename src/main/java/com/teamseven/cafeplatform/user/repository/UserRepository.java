package com.teamseven.cafeplatform.user.repository;

import com.teamseven.cafeplatform.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}