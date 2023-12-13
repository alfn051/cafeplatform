package com.teamseven.cafeplatform.domain.propensity.dto;

import com.teamseven.cafeplatform.domain.cafe.common.Category;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.propensity.common.Scenery;
import com.teamseven.cafeplatform.domain.propensity.entity.*;
import com.teamseven.cafeplatform.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserPropensityDTO {
    private long userId;
    private int sceneryImportance;
    private int interiorImportance;
    private int menuImportance;
    private int mood;
    private List<Scenery> sceneryPreferredList;
    private List<Category> menuPreferredList;

    public UserPropensity toEntity(User user) {
        return UserPropensity.builder()
                .sceneryImportance(sceneryImportance)
                .interiorImportance(interiorImportance)
                .menuImportance(menuImportance)
                .mood(mood)
                .user(user)
                .build();
    }

    public MenuPreferred toMenuPreferred(Category category, UserPropensity userPropensity){
        return MenuPreferred.builder()
                .category(category)
                .userPropensity(userPropensity)
                .build();
    }

    public SceneryPreferred toSceneryPreferred(Scenery scenery, UserPropensity userPropensity) {
        return SceneryPreferred.builder()
                .scenery(scenery)
                .userPropensity(userPropensity)
                .build();
    }
}
