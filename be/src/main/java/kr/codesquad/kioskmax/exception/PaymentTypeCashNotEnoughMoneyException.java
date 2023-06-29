package kr.codesquad.kioskmax.exception;

import static kr.codesquad.kioskmax.exception.ErrorCode.PaymentTypeCashNotEnoughMoney;

public class PaymentTypeCashNotEnoughMoneyException extends ApiException {

    public PaymentTypeCashNotEnoughMoneyException() {
        super(PaymentTypeCashNotEnoughMoney.getStatus(),
                PaymentTypeCashNotEnoughMoney.getMessage());
    }
}
