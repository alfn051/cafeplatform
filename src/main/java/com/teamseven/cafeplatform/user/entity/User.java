package com.teamseven.cafeplatform.user.entity;

import com.teamseven.cafeplatform.attendence.entity.AttendanceBoard;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.propensity.entity.UserPropensity;
import com.teamseven.cafeplatform.user.common.Gender;
import com.teamseven.cafeplatform.user.common.UserClassification;
import com.teamseven.cafeplatform.user.common.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private UserState state;
    @Enumerated(EnumType.STRING)
    private UserClassification classification;
    private long point;

    @OneToMany(mappedBy = "follower", orphanRemoval = true)
    private List<Follow> follows = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AttendanceBoard> attendanceBoards = new ArrayList<>();

    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private UserPropensity userPropensity;

}