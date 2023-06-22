package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.repository.CategoryRepository;
import kr.codesquad.kioskmax.service.dto.CategoryInformation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public List<CategoryInformation> findAllCategories() {
        return null;
    }
}
