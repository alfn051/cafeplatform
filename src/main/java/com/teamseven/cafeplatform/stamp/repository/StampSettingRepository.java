package com.teamseven.cafeplatform.stamp.repository;

import com.teamseven.cafeplatform.stamp.entity.StampSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StampSettingRepository extends JpaRepository<StampSetting, Long> {
}