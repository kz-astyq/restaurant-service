package kz.astyq.restaurantservice.core.exception;

import kz.astyq.restaurantservice.core.util.ErrorCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServiceValidationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private Object[] arguments;
    public static final HttpStatus STATUS = HttpStatus.BAD_REQUEST;

    public ServiceValidationException(ErrorCode errorCode, String message, Object... arguments) {
        this.message = message;
        this.errorCode = errorCode;
        this.arguments = arguments;
    }

    public ServiceValidationException( ErrorCode errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
