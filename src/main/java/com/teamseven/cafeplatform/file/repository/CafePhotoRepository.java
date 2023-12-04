package com.teamseven.cafeplatform.file.repository;

import com.teamseven.cafeplatform.file.entity.CafePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafePhotoRepository extends JpaRepository<CafePhoto, Long> {
}