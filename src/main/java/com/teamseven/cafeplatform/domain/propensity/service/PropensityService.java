package com.teamseven.cafeplatform.domain.propensity.service;

import com.teamseven.cafeplatform.domain.cafe.common.Category;
import com.teamseven.cafeplatform.domain.cafe.dto.CafeDisplayDTO;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.propensity.dto.CafePropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.dto.UserPropensityDTO;
import com.teamseven.cafeplatform.domain.propensity.entity.*;
import com.teamseven.cafeplatform.domain.propensity.repository.*;
import com.teamseven.cafeplatform.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropensityService {
    private final CafePropensityRepository cafePropensityRepository;
    private final MenuFocusedRepository menuFocusedRepository;
    private final MenuPreferredRepository menuPreferredRepository;
    private final SceneryPreferredRepository sceneryPreferredRepository;
    private final UserPropensityRepository userPropensityRepository;

    private final double weighting = 0.9;

    @Transactional
    public CafePropensity setCafePropensity(CafePropensityDTO dto, Cafe cafe) {
        CafePropensity cafePropensity = cafePropensityRepository.save(dto.toEntity(cafe));
        menuFocusedRepository.saveAll(dto.getMenuFocusedList().stream()
                .map(category -> dto.toMenuFocused(category, cafePropensity))
                .collect(Collectors.toList()));
        return cafePropensity;
    }

    @Transactional
    public UserPropensity setUserPropensity(UserPropensityDTO dto, User user) {
        UserPropensity userPropensity = userPropensityRepository.save(dto.toEntity(user));
        menuPreferredRepository.saveAll(dto.getMenuPreferredList().stream()
                .map(category -> dto.toMenuPreferred(category, userPropensity))
                .collect(Collectors.toList()));
        sceneryPreferredRepository.saveAll(dto.getSceneryPreferredList().stream()
                .map(scenery -> dto.toSceneryPreferred(scenery, userPropensity))
                .collect(Collectors.toList()));
        return userPropensity;
    }

    public double calculatePropensity(UserPropensity userProp, CafePropensity cafeProp) {
        int sceneryImportance = Math.abs(userProp.getSceneryImportance() - cafeProp.getSceneryImportance());
        int interiorImportance = Math.abs(userProp.getInteriorImportance() - cafeProp.getInteriorImportance());
        int menuImportance = Math.abs(userProp.getMenuImportance() - cafeProp.getMenuImportance());
        int mood = Math.abs(userProp.getMood() - cafeProp.getMood());
        double fitness = (double) (sceneryImportance + interiorImportance + menuImportance + mood) / 4;

        for (SceneryPreferred sp : userProp.getSceneryPreferredList()) {
            if (sp.getScenery() == cafeProp.getSceneryType()) {
                fitness = fitness * weighting;
            }
        }
        for (MenuPreferred mp : userProp.getMenuPreferredList()) {
            for (MenuFocused mf : cafeProp.getMenuFocusedList()) {
                if(mp.getCategory() == mf.getCategory()){
                    fitness = fitness * weighting;
                }
            }
        }

        return fitness;
    }

}
