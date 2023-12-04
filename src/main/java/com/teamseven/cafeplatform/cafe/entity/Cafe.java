package com.teamseven.cafeplatform.cafe.entity;

import com.teamseven.cafeplatform.cafe.common.CafeState;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.file.entity.CafePhoto;
import com.teamseven.cafeplatform.file.entity.Photo;
import com.teamseven.cafeplatform.propensity.entity.CafePropensity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cafe")
public class Cafe extends BaseEntity {
    private String name;
    private String phone;
    private String businessNum;
    private String baseAddress;
    private String detailAddress;
    private int postalCode;
    private String introduction;
    @Enumerated(EnumType.STRING)
    private CafeState state;
    private double ratingAverage;
    private int color;
    private boolean partnership;

    @OneToOne(mappedBy = "cafe", orphanRemoval = true)
    private CafePropensity cafePropensity;

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    private List<CafePhoto> cafePhotos = new ArrayList<>();

}