package kr.codesquad.kioskmax.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MenuRank {

    private final Long id;
    private final Long menuId;
    private final LocalDate sellAt;

    @Builder
    private MenuRank(Long id, Long menuId, LocalDate sellAt) {
        this.id = id;
        this.menuId = menuId;
        this.sellAt = sellAt;
    }
}
