package kr.codesquad.kioskmax.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import kr.codesquad.kioskmax.exception.PaymentTypeCardNotEnoughMoneyException;

import java.util.Arrays;
import java.util.function.Consumer;

public enum PaymentType {

    CARD(0, PaymentProcess.cardPayment),
    CASH(1, PaymentProcess.cashPayment);

    private final int paymentTypeCode;
    private final Consumer<Double> consumer;

    PaymentType(int paymentTypeCode, Consumer<Double> consumer) {
        this.paymentTypeCode = paymentTypeCode;
        this.consumer = consumer;
    }

    @JsonCreator
    public static PaymentType findByPaymentTypeCode(int paymentType) {
        return Arrays.stream(values())
                .filter(paymentTypes -> paymentTypes.paymentTypeCode == paymentType)
                .findAny()
                .orElseThrow();
    }

    public void processPayment(Double randomValue) {
        consumer.accept(randomValue);
    }

    @JsonValue
    public int getPaymentTypeCode() {
        return paymentTypeCode;
    }

    private static class PaymentProcess {

        private static final Consumer<Double> cardPayment = randomValue ->  {
            try {
                if (randomValue > 0.5) {
                    throw new PaymentTypeCardNotEnoughMoneyException();
                }

                long waitingTime = (long) (randomValue * 8000 + 3000);
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        private static final Consumer<Double> cashPayment = randomValue ->  {
        };
    }
}
