package kr.codesquad.kioskmax.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import kr.codesquad.kioskmax.annotation.ControllerTest;
import kr.codesquad.kioskmax.service.CategoryService;
import kr.codesquad.kioskmax.service.dto.CategoryInformation;
import kr.codesquad.kioskmax.service.dto.MenuInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ControllerTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리가 있을 때 카테고리 전체 조회하면 모든 카테고리와 메뉴들을 반환한다.")
    void getCategories() throws Exception {
        // given
        CategoryInformation 커피_카테고리_정보 = 카테고리_정보_생성(1L, "커피", 커피_카테고리_메뉴_정보들_생성());
        CategoryInformation 라떼_카테고리_정보 = 카테고리_정보_생성(2L, "라떼", 라떼_카테고리_메뉴_정보들_생성());

        given(categoryService.findAllCategories()).willReturn(List.of(커피_카테고리_정보, 라떼_카테고리_정보));

        // when
        ResultActions resultActions = mockMvc.perform(get("/categories"))
            .andDo(MockMvcRestDocumentation.document("categories",
                Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                responseFields(
                    fieldWithPath("[]").description("카데고리들"),
                    fieldWithPath("[].id").description("카테고리 아이디"),
                    fieldWithPath("[].name").description("카테고리명"),
                    fieldWithPath("[].menus").description("카테고리 메뉴들"),
                    fieldWithPath("[].menus[].id").description("메뉴 아이디"),
                    fieldWithPath("[].menus[].categoryId").description("카테고리 아이디"),
                    fieldWithPath("[].menus[].name").description("메뉴명"),
                    fieldWithPath("[].menus[].price").description("메뉴 가격"),
                    fieldWithPath("[].menus[].imageSrc").description("메뉴 이미지").optional()
                )));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$.[0].id").value(1L))
            .andExpect(jsonPath("$.[0].name").value("커피"))
            .andExpect(jsonPath("$.[1].menus.length()").value(2));
    }

    private CategoryInformation 카테고리_정보_생성(Long id, String name, List<MenuInformation> menuInformations) {
        return CategoryInformation.builder()
            .id(id)
            .name(name)
            .menuInformations(menuInformations)
            .build();
    }

    private List<MenuInformation> 커피_카테고리_메뉴_정보들_생성() {
        List<MenuInformation> menuInformations = new ArrayList<>();
        menuInformations.add(MenuInformation.builder()
            .id(1L)
            .categoryId(1L)
            .imageSrc("https://image.com")
            .price(4000L)
            .name("아메리카노")
            .build());
        return menuInformations;
    }

    private List<MenuInformation> 라떼_카테고리_메뉴_정보들_생성() {
        List<MenuInformation> menuInformations = new ArrayList<>();
        menuInformations.add(MenuInformation.builder()
            .id(2L)
            .categoryId(2L)
            .imageSrc("https://image.com")
            .price(4000L)
            .name("모카라떼")
            .build());
        menuInformations.add(MenuInformation.builder()
            .id(3L)
            .categoryId(2L)
            .imageSrc("https://image.com")
            .price(4000L)
            .name("밀크라떼")
            .build());
        return menuInformations;
    }
}
