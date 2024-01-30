package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.config.DirectionDTO;
import com.teamseven.cafeplatform.domain.cafe.common.CafeState;
import com.teamseven.cafeplatform.domain.cafe.common.StampImage;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private MultipartFile photo;

    public CafeRegistrationDTO setOwnerId(long ownerId){
        this.ownerId = ownerId;
        return this;
    }

    public List<MultipartFile> getPhotoList(){
        List<MultipartFile> list = new ArrayList<>();
        list.add(this.photo);
        return list;
    }

    public Cafe toEntity(User owner, String color, StampImage stampImage, DirectionDTO direction, CafeState state) {
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
                .partnerState(true)
                .longitude(direction.getLongitude())
                .latitude(direction.getLatitude())
                .owner(owner)
                .state(state)
                .build();

    }
}
