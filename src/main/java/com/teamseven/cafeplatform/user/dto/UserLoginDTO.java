package com.teamseven.cafeplatform.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginDTO {
    private String loginId;
    private String password;
}
