package com.teamseven.cafeplatform.propensity.entity;

import com.teamseven.cafeplatform.cafe.entity.Cafe;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.propensity.common.Scenery;
import jakarta.persistence.*;
import lombok.*;

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

}