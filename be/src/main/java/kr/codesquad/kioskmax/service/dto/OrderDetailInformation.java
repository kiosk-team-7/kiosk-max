package kr.codesquad.kioskmax.service.dto;

import kr.codesquad.kioskmax.domain.Menu;
import kr.codesquad.kioskmax.domain.Menus;
import kr.codesquad.kioskmax.domain.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderDetailInformation {

    private final String name;
    private final int count;

    @Builder
    private OrderDetailInformation(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public static List<OrderDetailInformation> from(List<OrderDetail> orderDetails, Menus menus) {
        return orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getMenuId, Collectors.summingInt(OrderDetail::getCount)))
                .entrySet().stream()
                .map(e -> new OrderDetailInformation(menus.getName(e.getKey()), e.getValue()))
                .collect(Collectors.toUnmodifiableList());
    }

    private static OrderDetailInformation from(OrderDetail orderDetail, Menu menu) {
        return OrderDetailInformation.builder()
                .name(menu.getName())
                .count(orderDetail.getCount())
                .build();
    }
}
