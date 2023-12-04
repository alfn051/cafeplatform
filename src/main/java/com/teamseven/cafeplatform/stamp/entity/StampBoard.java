package com.teamseven.cafeplatform.stamp.entity;

import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.stamp.common.BoardState;
import com.teamseven.cafeplatform.user.entity.CafeMember;
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
@Table(name = "stamp_board")
public class StampBoard extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "cafe_member_id")
    private CafeMember cafeMember;

    @ManyToOne
    @JoinColumn(name = "stamp_setting_id")
    private StampSetting stampSetting;

    private LocalDate expirationDate;
    private BoardState state;
    private int accruedStamp;

}