package com.teamseven.cafeplatform.domain.stamp.entity;

import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stamp_gift")
public class StampGift extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "stamp_board_id")
    private StampBoard stampBoard;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private boolean used;
    private LocalDate usedTime;

}