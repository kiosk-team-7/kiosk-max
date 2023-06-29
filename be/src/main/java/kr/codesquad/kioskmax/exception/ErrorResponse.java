package kr.codesquad.kioskmax.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;

    public int getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }
}
