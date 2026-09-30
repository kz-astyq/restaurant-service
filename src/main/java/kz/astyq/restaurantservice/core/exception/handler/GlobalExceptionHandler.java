package kz.astyq.restaurantservice.core.exception.handler;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.exception.dto.ExceptionResponse;
import kz.astyq.restaurantservice.core.i18n.MessageService;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageService messageService;

    @ExceptionHandler(ServiceValidationException.class)
    public ResponseEntity<ExceptionResponse> handleServiceValidation(ServiceValidationException e) {
        HttpStatus status = ServiceValidationException.STATUS;
        return build(status, e.getErrorCode(),
                messageService.getMessage(e.getMessage(), e.getArguments()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return build(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_ARGUMENT, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnexpected(Exception e) {
        log.error("Unexpected error", e);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.SYSTEM_ERROR, "Internal server error");
    }

    private ResponseEntity<ExceptionResponse> build(HttpStatus status, ErrorCode code, String message) {
        return ResponseEntity.status(status).body(ExceptionResponse.builder()
                .code(code)
                .status(status.name())
                .message(message)
                .build());
    }
}