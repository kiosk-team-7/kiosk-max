package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.domain.OrderDetail;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDetailRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderDetailRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(OrderDetail orderDetail) {
        String sql = "INSERT INTO order_detail (menu_id, orders_id, count, create_at, size, temperature, amount) "
                + "VALUES (:menuId, :ordersId, :count, now(), :size, :temperature, :amount)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("menuId", orderDetail.getMenuId())
                .addValue("ordersId", orderDetail.getOrdersId())
                .addValue("count", orderDetail.getCount())
                .addValue("size", orderDetail.getMenuSizeCode())
                .addValue("temperature", orderDetail.getMenuTemperatureCode())
                .addValue("amount", orderDetail.getAmount());

        jdbcTemplate.update(sql, parameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    public OrderDetail findById(Long id){
        String sql = "SELECT id, menu_id, orders_id, count, create_at, size, temperature, amount FROM order_detail WHERE id = :id";

        return jdbcTemplate.queryForObject(sql,Map.of("id",id),orderDetailMapper);
    }

    public List<OrderDetail> findAllByOrderId(long ordersId) {
        String sql = "SELECT id, menu_id, orders_id, count, create_at, size, temperature, amount FROM order_detail WHERE orders_id = :ordersId";

        return jdbcTemplate.query(sql, Map.of("ordersId",ordersId), orderDetailMapper);
    }

    private final RowMapper<OrderDetail> orderDetailMapper = (rs, rowNum) -> OrderDetail.builder()
            .id(rs.getLong("id"))
            .menuId(rs.getLong("menu_id"))
            .ordersId(rs.getLong("orders_id"))
            .count(rs.getInt("count"))
            .createAt(rs.getTimestamp("create_at").toLocalDateTime())
            .size(MenuSize.findBySizeCode(rs.getInt("size")))
            .temperature(MenuTemperature.findByTemperatureCode(rs.getInt("temperature")))
            .amount(rs.getLong("amount"))
            .build();
}
