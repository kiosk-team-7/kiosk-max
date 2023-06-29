package kr.codesquad.kioskmax.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.codesquad.kioskmax.domain.MenuSize;
import kr.codesquad.kioskmax.domain.MenuTemperature;
import kr.codesquad.kioskmax.service.dto.OrderDetailSaveInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
public class OrderDetailSaveRequest {

    @JsonProperty("id")
    private final Long menuId;
    private final int count;
    private final MenuSize size;
    private final MenuTemperature temperature;

    @Builder
    private OrderDetailSaveRequest(Long menuId, int count, MenuSize size, MenuTemperature temperature){
        this.menuId = menuId;
        this.count = count;
        this.size = size;
        this.temperature = temperature;
    }

    public static List<OrderDetailSaveInformation> toOrderDetailSaveInformation(List<OrderDetailSaveRequest> orderDetailSaveRequests) {
        return orderDetailSaveRequests.stream()
                .map(OrderDetailSaveRequest::toOrderDetailSaveInformation)
                .collect(Collectors.toList());
    }

    public OrderDetailSaveInformation toOrderDetailSaveInformation() {
        return OrderDetailSaveInformation.builder()
                .menuId(menuId)
                .count(count)
                .size(size)
                .temperature(temperature)
                .build();
    }
}
