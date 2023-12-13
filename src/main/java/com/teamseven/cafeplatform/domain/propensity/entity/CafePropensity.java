package com.teamseven.cafeplatform.domain.propensity.entity;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.propensity.common.Scenery;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cafe_propensity")
public class CafePropensity extends BaseEntity {
    private Scenery sceneryType;
    private int sceneryImportance;
    private int interiorImportance;
    private int menuImportance;
    private int mood;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToMany(mappedBy = "cafePropensity", orphanRemoval = true)
    private List<MenuFocused> menuFocusedList = new ArrayList<>();

}