package kr.codesquad.kioskmax.controller.dto;

import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.kioskmax.service.dto.MenuInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuResponse {

    private final Long id;
    private final Long categoryId;
    private final String name;
    private final Long price;
    private final String imageSrc;
    private final boolean isPopular;

    @Builder
    public MenuResponse(Long id, Long categoryId, String name, Long price, String imageSrc, boolean isPopular) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.imageSrc = imageSrc;
        this.isPopular = isPopular;
    }

    public static List<MenuResponse> from(List<MenuInformation> menuInformations) {
        return menuInformations.stream()
            .map(MenuResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public static MenuResponse from(MenuInformation menuInformation) {
        return MenuResponse.builder()
            .id(menuInformation.getId())
            .categoryId(menuInformation.getCategoryId())
            .name(menuInformation.getName())
            .price(menuInformation.getPrice())
            .imageSrc(menuInformation.getImageSrc())
            .isPopular(menuInformation.getIsPopular())
            .build();
    }

    public boolean getIsPopular() {
        return isPopular;
    }
}
