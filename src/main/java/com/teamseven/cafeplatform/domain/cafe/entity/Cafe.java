package com.teamseven.cafeplatform.domain.cafe.entity;

import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.file.entity.CafePhoto;
import com.teamseven.cafeplatform.domain.propensity.entity.CafePropensity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private Double ratingAverage;
    private int reviewCount;
    private int color;
    private boolean partnership;

    @OneToOne(mappedBy = "cafe", orphanRemoval = true)
    private CafePropensity cafePropensity;

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    private List<CafePhoto> cafePhotos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

}