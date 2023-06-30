package kr.codesquad.kioskmax.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Category {
    
    private final Long id;
    private final String name;
    private final LocalDateTime createdDateTime;

    @Builder
    private Category(Long id, String name, LocalDateTime createdDateTime) {
        this.id = id;
        this.name = name;
        this.createdDateTime = createdDateTime;
    }
}
