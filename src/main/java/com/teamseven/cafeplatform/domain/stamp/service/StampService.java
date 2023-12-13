package com.teamseven.cafeplatform.domain.stamp.service;

import com.teamseven.cafeplatform.domain.stamp.entity.StampGift;
import com.teamseven.cafeplatform.domain.stamp.repository.StampBoardRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampGiftRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampHistoryRepository;
import com.teamseven.cafeplatform.domain.stamp.repository.StampSettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StampService {
    private final StampBoardRepository stampBoardRepository;
    private final StampGiftRepository stampGiftRepository;
    private final StampHistoryRepository stampHistoryRepository;
    private StampSettingRepository stampSettingRepository;

    public StampGift getStampGiftById(long id) {
        return stampGiftRepository.findById(id).orElse(null);
    }
}
