package com.teamseven.cafeplatform.domain.propensity.entity;

import com.teamseven.cafeplatform.domain.cafe.common.Category;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_focused")
public class MenuFocused {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cafe_propensity_id")
    private CafePropensity cafePropensity;

    private Category category;

}