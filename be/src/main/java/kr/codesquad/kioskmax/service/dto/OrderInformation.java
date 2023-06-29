package kr.codesquad.kioskmax.service.dto;

import kr.codesquad.kioskmax.domain.Menus;
import kr.codesquad.kioskmax.domain.Order;
import kr.codesquad.kioskmax.domain.OrderDetail;
import kr.codesquad.kioskmax.domain.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class OrderInformation {

    private final int orderNumber;
    private final PaymentType paymentType;
    private final Long inputAmount;
    private final Long totalPrice;
    private final Long remain;
    private final List<OrderDetailInformation> orderDetailInformations;

    @Builder
    private OrderInformation(int orderNumber, PaymentType paymentType, Long inputAmount, Long totalPrice, Long remain,
                             List<OrderDetailInformation> orderDetailInformations) {
        this.orderNumber = orderNumber;
        this.paymentType = paymentType;
        this.inputAmount = inputAmount;
        this.totalPrice = totalPrice;
        this.remain = remain;
        this.orderDetailInformations = orderDetailInformations;
    }

    public static OrderInformation from(Order order, List<OrderDetail> orderDetails, Menus menus) {
        return OrderInformation.builder()
                .orderNumber(order.getOrderNumber())
                .paymentType(order.getPaymentType())
                .inputAmount(order.getInputAmount())
                .totalPrice(order.getTotalPrice())
                .remain(order.getRemain())
                .orderDetailInformations(OrderDetailInformation.from(orderDetails, menus))
                .build();
    }
}
