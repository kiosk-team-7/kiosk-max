package kr.codesquad.kioskmax.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Menu {

    private final Long id;
    private final Long categoryId;
    private final String name;
    private final Long price;
    private final String imageSrc;
    private final boolean isPopular;
    private final LocalDateTime createdDateTime;

    @Builder
    public Menu(Long id, Long categoryId, String name, Long price, String imageSrc, boolean isPopular,
        LocalDateTime createdDateTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.price = price;
        this.imageSrc = imageSrc;
        this.isPopular = isPopular;
        this.createdDateTime = createdDateTime;
    }
}
