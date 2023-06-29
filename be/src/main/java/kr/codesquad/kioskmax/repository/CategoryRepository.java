package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.domain.Category;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CategoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CategoryRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Category> findAll() {
        String sql = "SELECT id, name, create_at FROM category";

        return jdbcTemplate.query(sql, categoryMapper);
    }

    private final RowMapper<Category> categoryMapper = (rs, rowNum) -> Category.builder()
        .id(rs.getLong("id"))
        .name(rs.getString("name"))
        .createdDateTime(rs.getTimestamp("create_at").toLocalDateTime())
        .build();
}
