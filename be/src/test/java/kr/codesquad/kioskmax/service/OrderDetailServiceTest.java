package kr.codesquad.kioskmax.service;

import kr.codesquad.kioskmax.annotation.ServiceTest;
import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.domain.OrderDetail;
import kr.codesquad.kioskmax.repository.MenuRankRepository;
import kr.codesquad.kioskmax.repository.OrderDetailRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ServiceTest
public class OrderDetailServiceTest {

    @InjectMocks
    private OrderDetailService orderDetailService;

    @Mock
    private MenuRankRepository menuRankRepository;

    @Mock
    private OrderDetailRepository orderDetailRepository;


    @DisplayName("주문서 상세 내역들을 가져올 때 주문서 번호를 입력 받으면 주문서 상세 내역들을 반환한다.")
    @Test
    void findAllByOrderId() {
        // given
        Long orderId = 1L;
        given(orderDetailRepository.findAllByOrderId(1L))
                .willReturn(createDummyOrderDetails());

        // when
        List<OrderDetail> actual = orderDetailService.findAllByOrderId(orderId);

        // then
        assertThat(actual.size()).isEqualTo(5);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(createDummyOrderDetails());
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
}
