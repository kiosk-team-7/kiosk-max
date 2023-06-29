package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.annotation.RepositoryTest;
import kr.codesquad.kioskmax.domain.Category;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

@RepositoryTest
class CategoryRepositoryTest {

    private final CategoryRepository repository;

    @Autowired
    public CategoryRepositoryTest(DataSource dataSource) {
        this.repository = new CategoryRepository(dataSource);
    }

    // AAA일 때, B를 하면 C가 된다.
    @DisplayName("카테고리가 있을 때 카테고리 전체 조회하면 모든 카테고리를 반환한다.")
    @Test
    void findAll() {
        //given

        // when
        List<Category> actual = repository.findAll();

        // then
        Assertions.assertThat(actual.size()).isEqualTo(5);
    }
}
