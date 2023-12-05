package com.teamseven.cafeplatform.domain.stamp.entity;

import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.stamp.common.StampExpiration;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stamp_setting")
public class StampSetting extends BaseEntity {
    private int total_stamp;
    private StampExpiration expiration;
    private long criterionAmount;
    private boolean eventFirst;
    private boolean eventBirthday;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne
    @JoinColumn(name = "gift_first_id")
    private Menu giftFirst;

    @ManyToOne
    @JoinColumn(name = "gift_second_id")
    private Menu giftSecond;


}