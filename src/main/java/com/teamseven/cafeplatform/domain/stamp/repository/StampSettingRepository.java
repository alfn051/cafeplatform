package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.stamp.entity.StampSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StampSettingRepository extends JpaRepository<StampSetting, Long> {
    Optional<StampSetting> findFirstByCafeIdOrderByCreateTimeDesc(Long cafeId);
}