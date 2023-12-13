package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Partnership;
import com.teamseven.cafeplatform.domain.user.entity.User;
import com.teamseven.cafeplatform.file.entity.Photo;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
public class CafeRegistrationDTO {
    private String name;
    private String phone;
    private String businessNum;
    private String baseAddress;
    private String detailAddress;
    private int postalCode;
    private String introduction;
    private long ownerId;
    private List<MultipartFile> photoList;

    public CafeRegistrationDTO setOwnerId(long ownerId){
        this.ownerId = ownerId;
        return this;
    }

    public Cafe toEntity(User owner, String color, StampImage stampImage, DirectionDTO direction) {
        return Cafe.builder()
                .name(name)
                .phone(phone)
                .businessNum(businessNum)
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .postalCode(postalCode)
                .introduction(introduction)
                .ratingAverage(0.0)
                .reviewCount(0)
                .color(color)
                .stampImage(stampImage)
                .partnership(true)
                .longitude(direction.getLongitude())
                .latitude(direction.getLatitude())
                .owner(owner)
                .build();

    }
}
