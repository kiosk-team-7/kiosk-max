package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.domain.Order;
import kr.codesquad.kioskmax.domain.PaymentType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Map;

@Repository
public class OrderRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(Order order) {
        String sql = "INSERT INTO orders (payment_type, input_amount, total_price, remain, order_number, order_at) " +
                "                VALUES (:paymentType, :inputAmount, :totalPrice, :remain, " +
                "                (select o.order_number from " +
                "                (select count(id) + 1 as order_number from orders where DATE(order_at) = CURDATE()) o) " +
                "                ,now())";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("paymentType", order.getPaymentTypeCode())
                .addValue("inputAmount", order.getInputAmount())
                .addValue("totalPrice", order.getTotalPrice())
                .addValue("remain", order.getRemain());
        jdbcTemplate.update(sql, parameters, keyHolder);

        //jdbcTemplate.update(sql, parameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public Order findById(long id) {
        String sql = "SELECT id, payment_type, input_amount, total_price, remain, order_at, order_number FROM orders WHERE id = :id";

        return jdbcTemplate.queryForObject(sql, Map.of("id", id), orderMapper);
    }

    private final RowMapper<Order> orderMapper = (rs, rowNum) -> Order.builder()
            .id(rs.getLong("id"))
            .paymentType(PaymentType.findByPaymentTypeCode(rs.getInt("payment_type")))
            .inputAmount(rs.getLong("input_amount"))
            .totalPrice(rs.getLong("total_price"))
            .remain(rs.getLong("remain"))
            .orderAt(rs.getTimestamp("order_at").toLocalDateTime())
            .orderNumber(rs.getInt("order_number"))
            .build();
}
