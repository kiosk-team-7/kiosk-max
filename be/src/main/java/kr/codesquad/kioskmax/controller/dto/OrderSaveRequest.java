package kr.codesquad.kioskmax.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kr.codesquad.kioskmax.domain.PaymentType;
import kr.codesquad.kioskmax.service.dto.OrderSaveInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderSaveRequest {

    private final List<OrderDetailSaveRequest> menus;
    private final Long inputAmount;
    private final Long totalPrice;
    private final PaymentType paymentType;

    @Builder
    private OrderSaveRequest(List<OrderDetailSaveRequest> orderDetailSaveRequests, Long inputAmount,
                             Long totalPrice, PaymentType paymentType) {
        this.menus = orderDetailSaveRequests;
        this.inputAmount = inputAmount;
        this.totalPrice = totalPrice;
        this.paymentType = paymentType;
    }

    public OrderSaveInformation toOrderSaveInformation() {
        return OrderSaveInformation.builder()
                .orderDetailSaveInformations(OrderDetailSaveRequest.toOrderDetailSaveInformation(menus))
                .inputAmount(inputAmount)
                .totalPrice(totalPrice)
                .paymentType(paymentType)
                .build();
    }
}
