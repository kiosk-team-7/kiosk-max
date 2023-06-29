package kr.codesquad.kioskmax.controller.dto;

import kr.codesquad.kioskmax.domain.PaymentType;
import kr.codesquad.kioskmax.service.dto.OrderDetailInformation;
import kr.codesquad.kioskmax.service.dto.OrderInformation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderResponse {

    private final int orderNumber;
    private final List<OrderDetailInformation> menus;
    private final Long inputAmount;
    private final Long totalPrice;
    private final PaymentType paymentType;
    private final Long remain;


    @Builder
    private OrderResponse(int orderNumber, List<OrderDetailInformation> menus, Long inputAmount, Long totalPrice, PaymentType paymentType, Long remain) {
        this.orderNumber = orderNumber;
        this.menus = menus;
        this.inputAmount = inputAmount;
        this.totalPrice = totalPrice;
        this.paymentType = paymentType;
        this.remain = remain;
    }

    public static OrderResponse from(OrderInformation orderInformation) {
        return OrderResponse.builder()
                .orderNumber(orderInformation.getOrderNumber())
                .menus(orderInformation.getOrderDetailInformations())
                .inputAmount(orderInformation.getInputAmount())
                .totalPrice(orderInformation.getTotalPrice())
                .paymentType(orderInformation.getPaymentType())
                .remain(orderInformation.getRemain())
                .build();
    }
}
