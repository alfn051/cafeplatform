package com.teamseven.cafeplatform.domain.propensity.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_propensity")
public class UserPropensity extends BaseEntity {
    private int sceneryImportance;
    private int interiorImportance;
    private int menuImportance;
    private int mood;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userPropensity", orphanRemoval = true)
    private List<SceneryPreferred> sceneryPreferredList = new ArrayList<>();

    @OneToMany(mappedBy = "userPropensity", orphanRemoval = true)
    private List<MenuPreferred> menuPreferredList = new ArrayList<>();

}