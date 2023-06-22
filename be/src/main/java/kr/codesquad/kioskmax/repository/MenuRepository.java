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
        String sql = "SELECT m.id, m.category_id, m.name, m.price, m.image, m.create_at "
                     + "FROM menu m "
                     + "LEFT OUTER JOIN (SELECT menu_id, COUNT(menu_id) AS menu_count "
            + "                            FROM menu_rank "
            + "                           WHERE sell_at = :now "
            + "                           GROUP BY menu_id ) mr ON mr.menu_id = m.id "
                    + "INNER JOIN category c ON c.id = m.category_id "
                    + "WHERE c.id = :categoryId "
                    + "ORDER BY menu_count DESC";

        return jdbcTemplate.query(sql, Map.of("categoryId", categoryId, "now", LocalDate.now()), menuMapper);
    }

    private final RowMapper<Menu> menuMapper = (rs, rowNum) -> Menu.builder()
        .id(rs.getLong("id"))
        .categoryId(rs.getLong("category_id"))
        .name(rs.getString("name"))
        .price(rs.getLong("price"))
        .image(rs.getString("image"))
        .createdDateTime(rs.getTimestamp("create_at").toLocalDateTime())
        .build();

}
