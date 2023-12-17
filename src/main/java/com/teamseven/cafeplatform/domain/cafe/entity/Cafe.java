package com.teamseven.cafeplatform.domain.cafe.entity;

import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.file.entity.CafePhoto;
import com.teamseven.cafeplatform.domain.propensity.entity.CafePropensity;
import com.teamseven.cafeplatform.file.entity.Photo;
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
    private int reviewCount;
    @Column(nullable = true)
    private Double ratingAverage;
    private String color;
    private boolean partnerState;
    private StampImage stampImage;
    private double longitude; //경도
    private double latitude; //위도

    @OneToOne(mappedBy = "cafe", orphanRemoval = true)
    private CafePropensity cafePropensity;

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    private List<CafePhoto> cafePhotos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", orphanRemoval = true)
    private List<Partnership> partnerships = new ArrayList<>();

    @OneToMany(mappedBy = "cafe")
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "cafe")
    private List<MonthlyRecord> monthlyRecords = new ArrayList<>();


    public CafePhoto getFirstPhoto(){
        return this.getCafePhotos().stream().findFirst().orElse(null);
    }
}