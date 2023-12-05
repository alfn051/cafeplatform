package com.teamseven.cafeplatform.domain.stamp.repository;

import com.teamseven.cafeplatform.domain.stamp.entity.StampSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampSettingRepository extends JpaRepository<StampSetting, Long> {
}