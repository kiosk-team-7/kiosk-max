package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.annotation.ServiceTest;
import kr.codesquad.kioskmax.domain.*;
import kr.codesquad.kioskmax.exception.PaymentTypeCardNotEnoughMoneyException;
import kr.codesquad.kioskmax.exception.PaymentTypeCashNotEnoughMoneyException;
import kr.codesquad.kioskmax.repository.MenuRepository;
import kr.codesquad.kioskmax.repository.OrderDetailRepository;
import kr.codesquad.kioskmax.repository.OrderRepository;
import kr.codesquad.kioskmax.service.dto.OrderDetailInformation;
import kr.codesquad.kioskmax.service.dto.OrderDetailSaveInformation;
import kr.codesquad.kioskmax.service.dto.OrderInformation;
import kr.codesquad.kioskmax.service.dto.OrderSaveInformation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ServiceTest
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private RandomGenerator randomGenerator;

    @DisplayName("현금결제시 투입금액이 결제금액이상이면 주문서가 성공적으로 만들어 진다.")
    @Test
    void saveCashPaymentSuccessTest() {
        //given
        given(orderRepository.save(any())).willReturn(10L);
        given(orderRepository.findById(10L)).willReturn(createCashDummyOrder());
        given(orderDetailRepository.save(any())).willReturn(5L);
        given(orderDetailRepository.findAllByOrderId(10L)).willReturn(createDummyOrderDetails());
        given(menuRepository.findAll()).willReturn(createDummyMenuList());

        //when
        OrderInformation actual = orderService.save(createDummyCashOrderSaveInformation());

        //then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(createCashOrderInformation());
    }

    @DisplayName("현금결제시 투입금액이 결제금액미만이면 PaymentTypeCashNotEnoughMoneyException이 발생한다.")
    @Test
    void saveCashPaymentFailTest() {
        //given
        given(menuRepository.findAll()).willReturn(createDummyMenuList());

        //when & then
        Assertions.assertThatThrownBy(() -> orderService.save(createDummyCashOrderSaveInformationAmountShort()))
                .isInstanceOf(PaymentTypeCashNotEnoughMoneyException.class);
    }

    @DisplayName("카드 결제에 성공하면 주문서가 성공적으로 만들진다.")
    @Test
    void saveCardPaymentSuccessTest() {
        //given
        given(randomGenerator.getRandom()).willReturn(0.4);
        given(orderRepository.save(any())).willReturn(10L);
        given(orderRepository.findById(10L)).willReturn(createCashDummyOrder());
        given(orderDetailRepository.save(any())).willReturn(5L);
        given(orderDetailRepository.findAllByOrderId(10L)).willReturn(createDummyOrderDetails());
        given(menuRepository.findAll()).willReturn(createDummyMenuList());

        //when
        OrderInformation actual = orderService.save(createDummyCardOrderSaveInformation());

        //then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(createCashOrderInformation());
    }

    @DisplayName("카드 결제에 실패하면 PaymentTypeCardNotEnoughMoneyException가 발생한다")
    @Test
    void saveCardPaymentFailTest() {
        //given
        given(randomGenerator.getRandom()).willReturn(0.7);

        //when
        Assertions.assertThatThrownBy(() -> orderService.save(createDummyCardOrderSaveInformation()))
                .isInstanceOf(PaymentTypeCardNotEnoughMoneyException.class);
    }

    @DisplayName("카드 결제 성공시 3~7초의 대기 시간이 발생한다")
    @Test
    void waitingTimeValidationTest() {
        //given
        given(randomGenerator.getRandom()).willReturn(0.25);
        given(orderRepository.save(any())).willReturn(10L);
        given(orderRepository.findById(10L)).willReturn(createCashDummyOrder());
        given(orderDetailRepository.save(any())).willReturn(5L);
        given(orderDetailRepository.findAllByOrderId(10L)).willReturn(createDummyOrderDetails());
        given(menuRepository.findAll()).willReturn(createDummyMenuList());

        //when
        long startTime = System.currentTimeMillis();
        orderService.save(createDummyCardOrderSaveInformation());
        long endTime = System.currentTimeMillis();
        long workingTime = endTime - startTime;

        //then
        assertThat(workingTime).isCloseTo(5000L,within(100L));

    }

    private OrderSaveInformation createDummyCashOrderSaveInformation() {
        return OrderSaveInformation.builder()
                .orderDetailSaveInformations(createDummyOrderDetailSaveInformations())
                .inputAmount(100000L)
                .totalPrice(1234123L)
                .paymentType(PaymentType.CASH)
                .build();
    }

    private OrderSaveInformation createDummyCashOrderSaveInformationAmountShort() {
        return OrderSaveInformation.builder()
                .orderDetailSaveInformations(createDummyOrderDetailSaveInformations())
                .inputAmount(20000L)
                .totalPrice(1234123L)
                .paymentType(PaymentType.CASH)
                .build();
    }

    private OrderSaveInformation createDummyCardOrderSaveInformation() {
        return OrderSaveInformation.builder()
                .orderDetailSaveInformations(createDummyOrderDetailSaveInformations())
                .inputAmount(1000000L)
                .totalPrice(1234123L)
                .paymentType(PaymentType.CARD)
                .build();
    }

    private List<OrderDetailSaveInformation> createDummyOrderDetailSaveInformations() {
        OrderDetailSaveInformation dummy1 = OrderDetailSaveInformation.builder()
                .menuId(1L)
                .count(2)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.ICE)
                .build();

        OrderDetailSaveInformation dummy2 = OrderDetailSaveInformation.builder()
                .menuId(2L)
                .count(1)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();

        OrderDetailSaveInformation dummy3 = OrderDetailSaveInformation.builder()
                .menuId(3L)
                .count(1)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.ICE)
                .build();

        OrderDetailSaveInformation dummy4 = OrderDetailSaveInformation.builder()
                .menuId(1L)
                .count(3)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .build();

        OrderDetailSaveInformation dummy5 = OrderDetailSaveInformation.builder()
                .menuId(2L)
                .count(2)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .build();

        return new ArrayList<>(Arrays.asList(dummy1, dummy2, dummy3, dummy4, dummy5));
    }

    private List<Menu> createDummyMenuList() {
        Menu menu1 = Menu.builder()
                .id(1L)
                .categoryId(1L)
                .name("Menu1")
                .price(5000L)
                .imageSrc("menu1.jpg")
                .createdDateTime(LocalDateTime.now())
                .build();

        Menu menu2 = Menu.builder()
                .id(2L)
                .categoryId(1L)
                .name("Menu2")
                .price(6000L)
                .imageSrc("menu2.jpg")
                .createdDateTime(LocalDateTime.now())
                .build();

        Menu menu3 = Menu.builder()
                .id(3L)
                .categoryId(2L)
                .name("Menu3")
                .price(7000L)
                .imageSrc("menu3.jpg")
                .createdDateTime(LocalDateTime.now())
                .build();

        Menu menu4 = Menu.builder()
                .id(4L)
                .categoryId(2L)
                .name("Menu4")
                .price(8000L)
                .imageSrc("menu4.jpg")
                .createdDateTime(LocalDateTime.now())
                .build();

        return new ArrayList<>(Arrays.asList(menu1, menu2, menu3, menu4));
    }

    private Order createCashDummyOrder() {
        return Order.builder()
                .id(10L)
                .paymentType(PaymentType.CASH)
                .inputAmount(100000L)
                .totalPrice(50000L)
                .remain(50000L)
                .orderNumber(1)
                .build();
    }

    private List<OrderDetail> createDummyOrderDetails() {
        OrderDetail orderDetail1 = OrderDetail.builder()
                .id(1L)
                .menuId(1L)
                .ordersId(10L)
                .count(2)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.ICE)
                .amount(10000L)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .id(2L)
                .menuId(2L)
                .ordersId(10L)
                .count(1)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .amount(6000L)
                .build();
        OrderDetail orderDetail3 = OrderDetail.builder()
                .id(3L)
                .menuId(3L)
                .ordersId(10L)
                .count(1)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.ICE)
                .amount(7000L)
                .build();
        OrderDetail orderDetail4 = OrderDetail.builder()
                .id(4L)
                .menuId(1L)
                .ordersId(10L)
                .count(3)
                .size(MenuSize.LARGE)
                .temperature(MenuTemperature.HOT)
                .amount(15000L)
                .build();
        OrderDetail orderDetail5 = OrderDetail.builder()
                .id(5L)
                .menuId(2L)
                .ordersId(10L)
                .count(2)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .amount(6000L)
                .build();

        return List.of(orderDetail1, orderDetail2, orderDetail3, orderDetail4, orderDetail5);
    }

    private OrderInformation createCashOrderInformation() {
        return OrderInformation.builder()
                .paymentType(PaymentType.CASH)
                .inputAmount(100000L)
                .totalPrice(50000L)
                .remain(50000L)
                .orderNumber(1)
                .orderDetailInformations(createCashOrderDetailInformations())
                .build();
    }

    private List<OrderDetailInformation> createCashOrderDetailInformations() {
        OrderDetailInformation orderDetailInformation1 = OrderDetailInformation.builder()
                .name("Menu1")
                .count(5)
                .build();
        OrderDetailInformation orderDetailInformation2 = OrderDetailInformation.builder()
                .name("Menu2")
                .count(3)
                .build();
        OrderDetailInformation orderDetailInformation3 = OrderDetailInformation.builder()
                .name("Menu3")
                .count(1)
                .build();

        return List.of(orderDetailInformation1, orderDetailInformation2, orderDetailInformation3);
    }
}
