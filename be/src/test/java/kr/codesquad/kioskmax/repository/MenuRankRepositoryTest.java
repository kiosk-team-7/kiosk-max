package kr.codesquad.kioskmax.repository;

import kr.codesquad.kioskmax.annotation.RepositoryTest;
import kr.codesquad.kioskmax.domain.MenuRank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@RepositoryTest
public class MenuRankRepositoryTest {

    private final MenuRankRepository repository;

    @Autowired
    public MenuRankRepositoryTest(DataSource dataSource) {
        this.repository = new MenuRankRepository(dataSource);
    }

    @DisplayName("menuRank를 DB에 저장할수 있다.")
    @Test
    void saveTest() {
        //given
        MenuRank menuRank = createDummyMenuRank();

        //when
        Long actual = repository.save(menuRank);

        //then
        assertThat(actual).isEqualTo(82L);
    }

    private MenuRank createDummyMenuRank() {
        return MenuRank.builder()
                .menuId(1L)
                .sellAt(LocalDate.now())
                .build();
    }
}
