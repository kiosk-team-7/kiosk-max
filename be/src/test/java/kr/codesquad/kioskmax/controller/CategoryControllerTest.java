package kr.codesquad.kioskmax.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import kr.codesquad.kioskmax.service.CategoryService;
import kr.codesquad.kioskmax.service.dto.CategoryInformation;
import kr.codesquad.kioskmax.service.dto.MenuInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리가 있을 때 카테고리 전체 조회하면 모든 카테고리와 메뉴들을 반환한다.")
    void getCategories() throws Exception {
        // given
        List<MenuInformation> menuInformations = new ArrayList<>();
        menuInformations.add(MenuInformation.builder()
            .id(1L)
            .categoryId(1L)
            .image("https://image")
            .price(4000L)
            .name("아메리카노")
            .build());
        List<CategoryInformation> categoryInformations = new ArrayList<>();
        categoryInformations.add(CategoryInformation.builder()
            .id(1L)
            .name("커피")
            .menuInformations(menuInformations)
            .build());

        given(categoryService.findAllCategories()).willReturn(categoryInformations);

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/categories"));

        // then
        resultActions
            .andExpect(status().isOk())
            .andDo(print());
    }
}



