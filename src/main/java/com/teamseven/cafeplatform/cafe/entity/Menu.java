package com.teamseven.cafeplatform.cafe.entity;

import com.teamseven.cafeplatform.cafe.common.Category;
import com.teamseven.cafeplatform.cafe.common.MenuState;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.file.entity.Photo;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu")
public class Menu extends BaseEntity {
    private String name;
    private long price;
    private String information;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private MenuState state;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo photo;

}