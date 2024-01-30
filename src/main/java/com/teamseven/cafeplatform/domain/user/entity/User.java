package com.teamseven.cafeplatform.domain.user.entity;

import com.teamseven.cafeplatform.domain.attendence.entity.AttendanceBoard;
import com.teamseven.cafeplatform.config.BaseEntity;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import com.teamseven.cafeplatform.domain.propensity.entity.UserPropensity;
import com.teamseven.cafeplatform.domain.user.common.UserClassification;
import com.teamseven.cafeplatform.domain.user.common.UserState;
import com.teamseven.cafeplatform.domain.user.common.Gender;
import com.teamseven.cafeplatform.file.entity.Photo;
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

    @OneToMany(mappedBy = "follower")
    private List<Follow> followees = new ArrayList<>();

    @OneToMany(mappedBy = "followee")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AttendanceBoard> attendanceBoards = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserPropensity userPropensity;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "photo_id")
    private Photo photo;

    @OneToMany(mappedBy = "user")
    private List<CafeMember> cafeMembers = new ArrayList<>();

}