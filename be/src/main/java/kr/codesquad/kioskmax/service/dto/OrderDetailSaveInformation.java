package kr.codesquad.kioskmax.service.dto;

import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.domain.Menus;
import kr.codesquad.kioskmax.domain.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}

