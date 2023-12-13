package com.teamseven.cafeplatform.domain.cafe.dto;

import com.teamseven.cafeplatform.domain.cafe.common.Category;
import com.teamseven.cafeplatform.domain.cafe.common.MenuState;
import com.teamseven.cafeplatform.domain.cafe.entity.Cafe;
import com.teamseven.cafeplatform.domain.cafe.entity.Menu;
import com.teamseven.cafeplatform.file.entity.Photo;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class MenuCreateDTO {
    private String name;
    private long price;
    private String information;
    private Category category;
    private MenuState state;
    private long cafeId;
    private MultipartFile photo;

    public Menu toEntity(Cafe cafe) {
        return Menu.builder()
                .name(name)
                .price(price)
                .information(information)
                .category(category)
                .state(state)
                .cafe(cafe)
                .build();
    }
}
