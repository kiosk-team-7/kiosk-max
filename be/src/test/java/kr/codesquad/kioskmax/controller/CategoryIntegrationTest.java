package kr.codesquad.kioskmax.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.codesquad.kioskmax.annotation.IntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@IntegrationTest
public class CategoryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("카테고리가 있을 때 카테고리 전체 조회하면 모든 카테고리와 메뉴들을 반환한다.")
    @Test
    void getCategories() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/categories"))
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
                    fieldWithPath("[].menus[].imageSrc").description("메뉴 이미지"),
                    fieldWithPath("[].menus[].isPopular").description("인기 메뉴")
                )))
            .andDo(print());

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(5))
            .andExpect(jsonPath("$.[0].id").value(1L))
            .andExpect(jsonPath("$.[0].name").value("커피"))
            .andExpect(jsonPath("$.[1].menus.length()").value(4));
    }
}
