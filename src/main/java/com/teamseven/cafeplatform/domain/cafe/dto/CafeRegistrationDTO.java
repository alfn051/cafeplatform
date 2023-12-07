package com.teamseven.cafeplatform.domain.cafe.dto;

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
    private int color;
    private long ownerId;
    private List<MultipartFile> photoList;

    public CafeRegistrationDTO setOwnerId(long ownerId){
        this.ownerId = ownerId;
        return this;
    }

    public Cafe toEntity(User owner) {
        return Cafe.builder()
                .name(name)
                .phone(phone)
                .businessNum(businessNum)
                .baseAddress(baseAddress)
                .detailAddress(detailAddress)
                .postalCode(postalCode)
                .introduction(introduction)
                .ratingAverage(null)
                .reviewCount(0)
                .color(color)
                .partnership(true)
                .owner(owner)
                .build();

    }
}
