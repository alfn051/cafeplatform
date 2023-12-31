package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.entity.Review;
import com.teamseven.cafeplatform.domain.order.entity.Order;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ReviewDTO {
    private long orderId;
    private int rating;
    private String title;
    private String content;
    private MultipartFile photo;

    public List<MultipartFile> getPhotoList(){
        List<MultipartFile> photoList = new ArrayList<>();
        photoList.add(photo);
        return photoList;
    }

    public Review toEntity(Order order) {
        return Review.builder()
                .title(title)
                .rating(rating)
                .content(content)
                .order(order)
                .build();
    }
}
