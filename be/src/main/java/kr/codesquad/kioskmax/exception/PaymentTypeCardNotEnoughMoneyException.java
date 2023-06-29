package kr.codesquad.kioskmax.exception;

import static kr.codesquad.kioskmax.exception.ErrorCode.PaymentTypeCardNotEnoughMoney;

public class PaymentTypeCardNotEnoughMoneyException extends ApiException {

    public PaymentTypeCardNotEnoughMoneyException() {
        super(PaymentTypeCardNotEnoughMoney.getStatus(),
                PaymentTypeCardNotEnoughMoney.getMessage());
    }
}
