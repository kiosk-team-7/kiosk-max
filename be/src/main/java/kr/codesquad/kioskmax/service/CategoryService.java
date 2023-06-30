package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.repository.CategoryRepository;
import kr.codesquad.kioskmax.repository.MenuRankRepository;
import kr.codesquad.kioskmax.repository.MenuRepository;
import kr.codesquad.kioskmax.service.dto.CategoryInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;

    public List<CategoryInformation> findAllCategories() {
        List<CategoryInformation> categoryInformations = new ArrayList<>();
        categoryRepository.findAll()
            .forEach(category ->
                categoryInformations.add(CategoryInformation.of(category,
                    menuRepository.findAllByCategoryId(category.getId())))
            );
        return categoryInformations;
    }
}
