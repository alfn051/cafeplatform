package com.teamseven.cafeplatform.domain.propensity.dto;

import com.teamseven.cafeplatform.domain.cafe.common.Category;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.propensity.common.Scenery;
import com.teamseven.cafeplatform.domain.propensity.entity.CafePropensity;
import com.teamseven.cafeplatform.domain.propensity.entity.MenuFocused;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CafePropensityDTO {
    private long cafeId;
    private Scenery sceneryType;
    private int sceneryImportance;
    private int interiorImportance;
    private int menuImportance;
    private int mood;
    private List<Category> menuFocusedList;

    public CafePropensity toEntity(Cafe cafe) {
        return CafePropensity.builder()
                .sceneryType(sceneryType)
                .sceneryImportance(sceneryImportance)
                .interiorImportance(interiorImportance)
                .menuImportance(menuImportance)
                .mood(mood)
                .cafe(cafe)
                .build();
    }

    public MenuFocused toMenuFocused(Category category, CafePropensity cafePropensity){
        return MenuFocused.builder()
                .category(category)
                .cafePropensity(cafePropensity)
                .build();
    }
}
