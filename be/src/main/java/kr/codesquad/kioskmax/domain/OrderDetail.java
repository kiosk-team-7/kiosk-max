package kr.codesquad.kioskmax.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderDetail {

    private Long id;
    private final Long menuId;
    private final Long ordersId;
    private final int count;
    private final LocalDateTime createAt;
    private final MenuSize size;
    private final MenuTemperature temperature;
    private final Long amount;

    @Builder
    public OrderDetail(Long id, Long menuId, Long ordersId, int count, LocalDateTime createAt,
                       MenuSize size, MenuTemperature temperature, Long amount) {
        this.id = id;
        this.menuId = menuId;
        this.ordersId = ordersId;
        this.count = count;
        this.createAt = createAt;
        this.size = size;
        this.temperature = temperature;
        this.amount = amount;
    }

    public int getMenuSizeCode() {
        return size.getSizeCode();
    }

    public int getMenuTemperatureCode() {
        return temperature.getTemperatureCode();
    }
}
