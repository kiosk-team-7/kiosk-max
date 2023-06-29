package kr.codesquad.kioskmax.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // badRequest
    PaymentTypeCardNotEnoughMoney(HttpStatus.BAD_REQUEST, "결제 잔액이 부족합니다."),
    PaymentTypeCashNotEnoughMoney(HttpStatus.BAD_REQUEST, "결제 금액에 비해 투입 금액이 부족합니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
