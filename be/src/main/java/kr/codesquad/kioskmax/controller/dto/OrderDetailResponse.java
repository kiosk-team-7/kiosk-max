package kr.codesquad.kioskmax.controller.dto;

import kr.codesquad.kioskmax.service.dto.OrderDetailInformation;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderDetailResponse {

    private final String name;
    private final int count;

    @Builder
    private OrderDetailResponse(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public static List<OrderDetailResponse> from(List<OrderDetailInformation> orderDetailInformations) {
        return orderDetailInformations.stream()
                .map(OrderDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static OrderDetailResponse from(OrderDetailInformation orderDetailInformation) {
        return OrderDetailResponse.builder()
                .name(orderDetailInformation.getName())
                .count(orderDetailInformation.getCount())
                .build();
    }
}
