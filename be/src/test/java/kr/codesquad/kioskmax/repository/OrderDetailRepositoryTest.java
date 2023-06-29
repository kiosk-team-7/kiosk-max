package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.annotation.RepositoryTest;
import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.domain.OrderDetail;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

@RepositoryTest
public class OrderDetailRepositoryTest {

    private final OrderDetailRepository repository;

    @Autowired
    public OrderDetailRepositoryTest(DataSource dataSource) {
        this.repository = new OrderDetailRepository(dataSource);
    }

    @DisplayName("주문상세 내역을 DB에 저장한후 저장한 주문상세내역을 DB에서 가져온다.")
    @Test
    void saveTest() {
        //given
        OrderDetail orderDetail = 주문_상세내역_생성();

        //when
        Long resultId = repository.save(orderDetail);

        //then
        Assertions.assertThat(resultId).isEqualTo(1L);
    }

    @DisplayName("특정 주문서 id를 가진 모든 주문 상세 내역을 DB에서 가져올수 있다. ")
    @Test
    void findAllByOrderIdTest() {
        //given
        OrderDetail orderDetail = 주문_상세내역_생성();
        Long orderDetailId = repository.save(orderDetail);
        OrderDetail expected = repository.findById(orderDetailId);

        //when
        List<OrderDetail> result = repository.findAllByOrderId(orderDetail.getOrdersId());

        //then
        Assertions.assertThat(result.size()).isEqualTo(1L);

        Assertions.assertThat(result.get(0))
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    private OrderDetail 주문_상세내역_생성() {
        return OrderDetail.builder()
                .menuId(1L)
                .ordersId(1L)
                .count(2)
                .size(MenuSize.SMALL)
                .temperature(MenuTemperature.HOT)
                .amount(1L)
                .build();
    }
}
