package kr.codesquad.kioskmax.service.dto;

import kr.codesquad.kioskmax.domain.Category;
import kr.codesquad.kioskmax.domain.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryInformation {

    private final Long id;
    private final String name;
    private final List<MenuInformation> menuInformations;

    @Builder
    private CategoryInformation(Long id, String name, List<MenuInformation> menuInformations) {
        this.id = id;
        this.name = name;
        this.menuInformations = menuInformations;
    }

    public static CategoryInformation of(Category category, List<Menu> menus){
        return CategoryInformation.builder()
            .id(category.getId())
            .name(category.getName())
            .menuInformations(MenuInformation.from(menus))
            .build();
    }
}
