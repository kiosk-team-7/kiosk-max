package kr.codesquad.kioskmax.service.dto;

import kr.codesquad.kioskmax.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@Setter
public class OrderDetailSaveInformation {

    private final Long menuId;
    private final int count;
    private final MenuSize size;
    private final MenuTemperature temperature;

    @Builder
    private OrderDetailSaveInformation(Long menuId, int count, MenuSize size, MenuTemperature temperature) {
        this.menuId = menuId;
        this.count = count;
        this.size = size;
        this.temperature = temperature;
    }

    public OrderDetail toOrderDetail(long orderId, Menus menus) {
        return OrderDetail.builder()
                .menuId(getMenuId())
                .count(getCount())
                .size(getSize())
                .temperature(getTemperature())
                .amount(menus.calculatePrice(menuId, count))
                .ordersId(orderId)
                .build();
    }

    public List<MenuRank> toMenuRanks() {
        return IntStream.range(0, count)
                .mapToObj(i -> MenuRank.builder()
                        .menuId(menuId)
                        .sellAt(LocalDate.now())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }
}

