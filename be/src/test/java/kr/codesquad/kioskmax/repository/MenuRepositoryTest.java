package kr.codesquad.kioskmax.repository;

import java.util.List;

import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;

import kr.codesquad.kioskmax.domain.Menu;

@JdbcTest
@ActiveProfiles("test")
class MenuRepositoryTest {

	private final MenuRepository repository;

	@Autowired
	public MenuRepositoryTest(DataSource dataSource) {
		this.repository = new MenuRepository(dataSource);
	}

	@Test
	@DisplayName("특정 카테고리에 해당하는 메뉴를 판매순으로 정렬한후 모든 메뉴를 반환한다.")
	void findAll() {
		//given

		//when
		List<Menu> actual = repository.findAllByCategoryId(1L);

		//then
		Assertions.assertThat(actual.size()).isEqualTo(2);
		Assertions.assertThat(actual.get(0).getName()).isEqualTo("카페모카");
		Assertions.assertThat(actual.get(1).getName()).isEqualTo("아메리카노");

	}
}
