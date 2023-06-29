package kr.codesquad.kioskmax.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.codesquad.kioskmax.annotation.IntegrationTest;
import kr.codesquad.kioskmax.controller.dto.OrderDetailSaveRequest;
import kr.codesquad.kioskmax.controller.dto.OrderSaveRequest;
import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.domain.PaymentType;
import kr.codesquad.kioskmax.domain.RandomGenerator;
import kr.codesquad.kioskmax.exception.ErrorCode;
import kr.codesquad.kioskmax.exception.PaymentTypeCardNotEnoughMoneyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class OrderIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean(name = "randomGenerator")
    private RandomGenerator randomGenerator;

    @DisplayName("결제방식을 현금으로 할 때 메뉴들, 투입금액, 결제금액, 결제방식을 입력 받으면 주문서를 생성하고 영수증을 반환한다.")
    @Test
    void saveOrder() throws Exception {
        // given
        OrderDetailSaveRequest orderDetailSaveRequest = OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(1)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderDetailSaveRequest orderDetailSaveRequest2 = OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(1)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderDetailSaveRequest orderDetailSaveRequest3 = OrderDetailSaveRequest.builder()
                .menuId(2L)
                .count(2)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderSaveRequest request = OrderSaveRequest.builder()
                .orderDetailSaveRequests(List.of(orderDetailSaveRequest,
                        orderDetailSaveRequest2, orderDetailSaveRequest3))
                .inputAmount(15000L)
                .totalPrice(13000L)
                .paymentType(PaymentType.CASH)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcRestDocumentation.document("order/cash-success",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("menus[].id").description("메뉴 아이디"),
                                fieldWithPath("menus[].count").description("메뉴 수량"),
                                fieldWithPath("menus[].size").description("메뉴 옵션 사이즈"),
                                fieldWithPath("menus[].temperature").description("메뉴 옵션 온도"),
                                fieldWithPath("paymentType").description("결제 방식"),
                                fieldWithPath("inputAmount").description("투입 금액"),
                                fieldWithPath("totalPrice").description("주문 금액")
                        ),
                        responseFields(
                                fieldWithPath("orderNumber").description("주문번호"),
                                fieldWithPath("menus[].name").description("메뉴명"),
                                fieldWithPath("menus[].count").description("메뉴 수량"),
                                fieldWithPath("paymentType").description("결제 방식"),
                                fieldWithPath("inputAmount").description("투입 금액"),
                                fieldWithPath("totalPrice").description("주문 금액"),
                                fieldWithPath("remain").description("잔돈")
                        )))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(1))
                .andExpect(jsonPath("$.menus.length()").value(2))
                .andExpect(jsonPath("$.menus[0].name").value("아메리카노"))
                .andExpect(jsonPath("$.menus[0].count").value(2))
                .andExpect(jsonPath("$.menus[1].name").value("카페라떼"))
                .andExpect(jsonPath("$.menus[1].count").value(2))
                .andExpect(jsonPath("$.paymentType").value(PaymentType.CASH.getPaymentTypeCode()))
                .andExpect(jsonPath("$.inputAmount").value(15000L))
                .andExpect(jsonPath("$.totalPrice").value(13000L))
                .andExpect(jsonPath("$.remain").value(2000L));
    }

    @DisplayName("결제방식을 현금이고 투입금액이 결제금액보다 적을 때 메뉴들, 투입금액, 결제금액, 결제방식을 입력 받으면 에러를 반환한다.")
    @Test
    void saveOrder2() throws Exception {
        // given
        OrderDetailSaveRequest orderDetailSaveRequest = OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(1)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderDetailSaveRequest orderDetailSaveRequest2 = OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(1)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderDetailSaveRequest orderDetailSaveRequest3 = OrderDetailSaveRequest.builder()
                .menuId(2L)
                .count(2)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderSaveRequest request = OrderSaveRequest.builder()
                .orderDetailSaveRequests(List.of(orderDetailSaveRequest,
                        orderDetailSaveRequest2, orderDetailSaveRequest3))
                .inputAmount(11000L)
                .totalPrice(13000L)
                .paymentType(PaymentType.CASH)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcRestDocumentation.document("order/cash-fail",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("menus[].id").description("메뉴 아이디"),
                                fieldWithPath("menus[].count").description("메뉴 수량"),
                                fieldWithPath("menus[].size").description("메뉴 옵션 사이즈"),
                                fieldWithPath("menus[].temperature").description("메뉴 옵션 온도"),
                                fieldWithPath("paymentType").description("결제 방식"),
                                fieldWithPath("inputAmount").description("투입 금액"),
                                fieldWithPath("totalPrice").description("주문 금액")
                        ),
                        responseFields(
                                fieldWithPath("status").description("에러 상태 코드"),
                                fieldWithPath("message").description("에러 메시지")
                        )))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(ErrorCode.PaymentTypeCashNotEnoughMoney
                        .getMessage()));
    }


    @DisplayName("결제방식을 카드로 할 때 메뉴들, 투입금액, 결제금액, 결제방식을 입력 받으면 일정한 확률로 실패하고 에러를 반환한다.")
    @Test
     void saveOrder3() throws Exception {
        // given
        OrderDetailSaveRequest orderDetailSaveRequest = OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(1)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderDetailSaveRequest orderDetailSaveRequest2 = OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(1)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderDetailSaveRequest orderDetailSaveRequest3 = OrderDetailSaveRequest.builder()
                .menuId(2L)
                .count(2)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();
        OrderSaveRequest request = OrderSaveRequest.builder()
                .orderDetailSaveRequests(List.of(orderDetailSaveRequest,
                        orderDetailSaveRequest2, orderDetailSaveRequest3))
                .inputAmount(15000L)
                .totalPrice(13000L)
                .paymentType(PaymentType.CARD)
                .build();

        BDDMockito.given(randomGenerator.getRandom())
                .willThrow(new PaymentTypeCardNotEnoughMoneyException());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcRestDocumentation.document("order/card-fail",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        requestFields(
                                fieldWithPath("menus[].id").description("메뉴 아이디"),
                                fieldWithPath("menus[].count").description("메뉴 수량"),
                                fieldWithPath("menus[].size").description("메뉴 옵션 사이즈"),
                                fieldWithPath("menus[].temperature").description("메뉴 옵션 온도"),
                                fieldWithPath("paymentType").description("결제 방식"),
                                fieldWithPath("inputAmount").description("투입 금액"),
                                fieldWithPath("totalPrice").description("주문 금액")
                        ),
                        responseFields(
                                fieldWithPath("status").description("에러 상태 코드"),
                                fieldWithPath("message").description("에러 메시지")
                        )))
                .andDo(print());

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(ErrorCode.PaymentTypeCardNotEnoughMoney
                        .getMessage()));
    }
}
