package kr.codesquad.kioskmax.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.codesquad.kioskmax.annotation.ControllerTest;
import kr.codesquad.kioskmax.controller.dto.OrderDetailSaveRequest;
import kr.codesquad.kioskmax.controller.dto.OrderSaveRequest;
import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.domain.PaymentType;
import kr.codesquad.kioskmax.exception.PaymentTypeCardNotEnoughMoneyException;
import kr.codesquad.kioskmax.service.OrderService;
import kr.codesquad.kioskmax.service.dto.OrderDetailInformation;
import kr.codesquad.kioskmax.service.dto.OrderInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("웹에서 OrderSaveRequest 데이터를 받고 메소드를 실행하였을 때 영수증에 필요한 값들을 반환한다")
    @Test
    void checkOrder() throws Exception {
        // given
        OrderSaveRequest orderSaveRequest = 주문_요청_정보들_생성(주문_메뉴_상세_데이터들_생성(),
                                                        10000L,10000L, PaymentType.CASH);
        given(orderService.save(BDDMockito.any())).willReturn(createDummyOrderInformation(PaymentType.CASH));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/orders")
                .content(objectMapper.writeValueAsString(orderSaveRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.menus.length()").value(2))
                .andExpect(jsonPath("$.orderNumber").value(1))
                .andExpect(jsonPath("$.inputAmount").value(10000L))
                .andExpect(jsonPath("$.totalPrice").value(10000L))
                .andExpect(jsonPath("$.paymentType").value(PaymentType.CASH.getPaymentTypeCode()))
                .andDo(print());
    }
    @DisplayName("카드 결제 요청에 실패했을 때 예외를 에러 상태코드와 메세지를 던진다")
    @Test
    void checkWhenCardPaymentFailed() throws Exception{
        // given
        OrderSaveRequest orderSaveRequest = 주문_요청_정보들_생성(주문_메뉴_상세_데이터들_생성(),
                10000L,10000L, PaymentType.CASH);
        given(orderService.save(BDDMockito.any())).willThrow(new PaymentTypeCardNotEnoughMoneyException());

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/orders")
                .content(objectMapper.writeValueAsString(orderSaveRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("카드 결제 요청에 성공했을 때 HTTP status 200 코드를 웹에 전송한다")
    @Test
    void checkWhenCardPaymentSucceed() throws Exception {
        // given
        OrderSaveRequest orderSaveRequest = 주문_요청_정보들_생성(주문_메뉴_상세_데이터들_생성(),
                10000L,10000L, PaymentType.CARD);
        given(orderService.save(BDDMockito.any())).willReturn(createDummyOrderInformation(PaymentType.CARD));

        // when
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/orders")
                .content(objectMapper.writeValueAsString(orderSaveRequest))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(print());
    }

    private OrderSaveRequest 주문_요청_정보들_생성(List<OrderDetailSaveRequest> orderDetailSaveRequests, Long inputAmount,
                                          Long totalPrice, PaymentType paymentType) {
        return OrderSaveRequest.builder()
                .orderDetailSaveRequests(orderDetailSaveRequests)
                .inputAmount(inputAmount)
                .totalPrice(totalPrice)
                .paymentType(paymentType)
                .build();
    }

    private List<OrderDetailSaveRequest> 주문_메뉴_상세_데이터들_생성() {
        List<OrderDetailSaveRequest> temp = new ArrayList<>();
        temp.add(첫번째_주문_메뉴_상세_데이터_생성());
        temp.add(두번째_주문_메뉴_상세_데이터_생성());
        return temp;
    }

    private OrderDetailSaveRequest 첫번째_주문_메뉴_상세_데이터_생성() {
        return OrderDetailSaveRequest.builder()
                .menuId(1L)
                .count(2)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.ICE)
                .build();
    }

    private OrderDetailSaveRequest 두번째_주문_메뉴_상세_데이터_생성() {
        return OrderDetailSaveRequest.builder()
                .menuId(2L)
                .count(1)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .build();
    }

    private OrderInformation createDummyOrderInformation(PaymentType paymentType) {
        return OrderInformation.builder()
                .orderNumber(1)
                .paymentType(paymentType)
                .inputAmount(10000L)
                .totalPrice(10000L)
                .remain(0L)
                .orderDetailInformations(createDummyOrderDetailInformationList())
                .build();
    }

    private List<OrderDetailInformation> createDummyOrderDetailInformationList() {
        OrderDetailInformation dummy1 = OrderDetailInformation.builder()
                .name("아메리카노")
                .count(2)
                .build();

        OrderDetailInformation dummy2 = OrderDetailInformation.builder()
                .name("카페라떼")
                .count(1)
                .build();

        return new ArrayList<>(Arrays.asList(dummy1, dummy2));
    }
}
