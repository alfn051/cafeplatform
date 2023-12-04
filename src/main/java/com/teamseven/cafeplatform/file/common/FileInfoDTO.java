package com.teamseven.cafeplatform.file.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfoDTO {
    private String orgName;
    private String saveName;
}
