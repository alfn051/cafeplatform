package com.teamseven.cafeplatform.propensity.entity;

import com.teamseven.cafeplatform.cafe.common.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_preferred")
public class MenuPreferred {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_propensity_id")
    private UserPropensity userPropensity;

    private Category category;

}