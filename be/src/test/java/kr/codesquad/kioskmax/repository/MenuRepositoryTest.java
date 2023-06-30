package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.annotation.RepositoryTest;
import kr.codesquad.kioskmax.domain.Menu;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.List;

@RepositoryTest
class MenuRepositoryTest {

	private final MenuRepository repository;

	@Autowired
	public MenuRepositoryTest(DataSource dataSource) {
		this.repository = new MenuRepository(dataSource);
	}

	@DisplayName("특정 카테고리에 해당하는 메뉴를 판매순으로 정렬한후 모든 메뉴를 반환한다.")
	@Test
	void findAllTest() {
		//given

		//when
		List<Menu> actual = repository.findAllByCategoryId(1L);

		//then
		Assertions.assertThat(actual.size()).isEqualTo(5);
		Assertions.assertThat(actual.get(0).getName()).isEqualTo("아메리카노");
		Assertions.assertThat(actual.get(0).isPopular()).isTrue();
		Assertions.assertThat(actual.get(2).getName()).isEqualTo("카페라떼");
		Assertions.assertThat(actual.get(2).isPopular()).isFalse();
	}
}
