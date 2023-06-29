package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.annotation.RepositoryTest;
import kr.codesquad.kioskmax.domain.Order;
import kr.codesquad.kioskmax.domain.PaymentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

@RepositoryTest
public class OrderRepositoryTest {

    private final OrderRepository repository;

    @Autowired
    public OrderRepositoryTest(DataSource dataSource) {
        this.repository = new OrderRepository(dataSource);
    }

    @DisplayName("주문서를 DB에 저장한후 저장된 주문서를 DB에서 가져올수 있다.")
    @Test
    void saveTest() {
        //given
        Order order = 주문_정보_생성();

        //when
        Long resultId = repository.save(order);

        //then
        Assertions.assertThat(resultId).isEqualTo(1L);
    }

    @DisplayName("주문서 id를 통해 해당 주문서를 DB에서 가져올수 있다.")
    @Test
    void findByIdTest() {
        //given
        Order expected = 주문_정보_생성();
        Long ordersId = repository.save(expected);

        //when
        Order actual = repository.findById(ordersId);

        //then
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("orderNumber")
                .ignoringFields("id")
                .ignoringFields("orderAt")
                .isEqualTo(expected);

        Assertions.assertThat(actual.getId()).isEqualTo(1L);
        Assertions.assertThat(actual.getOrderNumber()).isEqualTo(1);
    }

    private Order 주문_정보_생성() {
        Order expected = Order.builder()
                .paymentType(PaymentType.CASH)
                .inputAmount(10000L)
                .totalPrice(9000L)
                .remain(1000L)
                .build();
        return expected;
    }
}
