package com.teamseven.cafeplatform.domain.cafe.repository;

import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeRepository extends JpaRepository<Cafe, Long> {
    public List<Cafe> findAllByState(CafeState state);

    public Optional<Cafe> findByOwnerId(Long ownerId);

    public List<Cafe> findAllByStateAndNameContaining(CafeState state, String keyword);
}