package kr.codesquad.kioskmax.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.stream.Collectors;
import kr.codesquad.kioskmax.service.dto.CategoryInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private final Long id;
    private final String name;

    @JsonProperty("menus")
    private final List<MenuResponse> menuResponses;

    @Builder
    private CategoryResponse(Long id, String name, List<MenuResponse> menuResponses) {
        this.id = id;
        this.name = name;
        this.menuResponses = menuResponses;
    }

    public static List<CategoryResponse> from(List<CategoryInformation> categoryInformations) {
        return categoryInformations.stream()
            .map(CategoryResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public static CategoryResponse from(CategoryInformation categoryInformation) {
        return CategoryResponse.builder()
            .id(categoryInformation.getId())
            .name(categoryInformation.getName())
            .menuResponses(MenuResponse.from(categoryInformation.getMenuInformations()))
            .build();
    }
}
