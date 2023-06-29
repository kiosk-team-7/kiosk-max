package kr.codesquad.kioskmax.service.dto;

import kr.codesquad.kioskmax.domain.Order;
import kr.codesquad.kioskmax.domain.PaymentType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class OrderSaveInformation {

    private final List<OrderDetailSaveInformation> orderDetailSaveInformations;
    private final Long inputAmount;
    private final Long totalPrice;
    private final PaymentType paymentType;

    @Builder
    public OrderSaveInformation(List<OrderDetailSaveInformation> orderDetailSaveInformations, Long inputAmount, Long totalPrice, PaymentType paymentType) {
        this.orderDetailSaveInformations = orderDetailSaveInformations;
        this.inputAmount = inputAmount;
        this.totalPrice = totalPrice;
        this.paymentType = paymentType;
    }

    public Order toOrder(long totalPrice) {
        return Order.builder()
                .inputAmount(inputAmount)
                .paymentType(paymentType)
                .totalPrice(totalPrice)
                .orderAt(LocalDateTime.now())
                .build();
    }

    public Map<Long, Integer> toMapForMenuIdAndMenuCount() {
        return orderDetailSaveInformations.stream()
                .collect(Collectors.groupingBy(OrderDetailSaveInformation::getMenuId,
                        Collectors.summingInt(OrderDetailSaveInformation::getCount)));
    }
}
