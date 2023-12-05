package com.teamseven.cafeplatform.domain.user.dto;

import com.teamseven.cafeplatform.domain.user.common.Gender;
import com.teamseven.cafeplatform.domain.user.common.UserClassification;
import com.teamseven.cafeplatform.domain.user.common.UserState;
import com.teamseven.cafeplatform.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserJoinDTO {
    private String loginId;
    private String password;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthday;
    private Gender gender;

    public User toEntity(){
        return User.builder()
                .loginId(loginId)
                .password(password)
                .name(name)
                .email(email)
                .phone(phone)
                .birthday(birthday)
                .gender(gender)
                .state(UserState.ACTIVE)
                .classification(UserClassification.NORMAL)
                .point(0)
                .build();
    }
}
