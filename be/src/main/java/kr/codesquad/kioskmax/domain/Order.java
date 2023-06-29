package kr.codesquad.kioskmax.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Order {

    private Long id;
    private final PaymentType paymentType;
    private final Long inputAmount;
    private final Long totalPrice;
    private final Long remain;
    private final LocalDateTime orderAt;
    private final int orderNumber;

    @Builder
    private Order(Long id, PaymentType paymentType, Long inputAmount, Long totalPrice, Long remain,
                 LocalDateTime orderAt, int orderNumber) {
        this.id = id;
        this.paymentType = paymentType;
        this.inputAmount = inputAmount;
        this.totalPrice = totalPrice;
        this.remain = remain;
        this.orderAt = orderAt;
        this.orderNumber = orderNumber;
    }

    public Long getRemain() {
        return inputAmount - totalPrice;
    }

    public int getPaymentTypeCode() {
        return paymentType.getPaymentTypeCode();
    }
}
