package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.domain.MenuRank;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class MenuRankRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MenuRankRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Long save(MenuRank menuRank) {
        String sql = "INSERT INTO menu_rank (menu_id,sell_at) " +
                "VALUES (:menuId, :sellAt)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("menuId",menuRank.getMenuId())
                .addValue("sellAt",menuRank.getSellAt());

        jdbcTemplate.update(sql,parameters,keyHolder);
        return keyHolder.getKey().longValue();
    }
}
