package kr.codesquad.kioskmax.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesquad.kioskmax.domain.Menu;

@Repository
public class MenuRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MenuRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Menu> findAllByCategoryId(Long categoryId) {
        String sql = "SELECT m.id, m.category_id, m.name, m.price, m.image_src, m.create_at, "
                          + "CASE WHEN mr.ranking <= 3 THEN TRUE ELSE FALSE END AS is_popular "
                     + "FROM menu m "
                     + "LEFT OUTER JOIN (SELECT menu_id, COUNT(menu_id) AS menu_count, "
                                             + "RANK() OVER (ORDER BY COUNT(menu_id) DESC) AS ranking "
            + "                            FROM menu_rank "
            + "                           WHERE sell_at = CURRENT_DATE "
            + "                           GROUP BY menu_id "
                                       + "HAVING COUNT(menu_id) >= 25 ) mr ON mr.menu_id = m.id "
                    + "WHERE m.category_id = :categoryId "
                    + "ORDER BY menu_count DESC";

        return jdbcTemplate.query(sql, Map.of("categoryId", categoryId), menuMapper);
    }

    private final RowMapper<Menu> menuMapper = (rs, rowNum) -> Menu.builder()
        .id(rs.getLong("id"))
        .categoryId(rs.getLong("category_id"))
        .name(rs.getString("name"))
        .price(rs.getLong("price"))
        .imageSrc(rs.getString("image_src"))
        .isPopular(rs.getBoolean("is_popular"))
        .createdDateTime(rs.getTimestamp("create_at").toLocalDateTime())
        .build();
}
