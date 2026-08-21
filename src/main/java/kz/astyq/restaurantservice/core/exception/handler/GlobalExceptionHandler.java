package kz.astyq.restaurantservice.core.exception.handler;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.exception.dto.ExceptionResponse;
import kz.astyq.restaurantservice.core.i18n.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageService messageService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceValidationException.class)
    @ResponseBody
    public ExceptionResponse serviceValidationExceptionHandler(ServiceValidationException e) {
        return ExceptionResponse.builder()
                .status(ServiceValidationException.STATUS.toString())
                .code(e.getErrorCode())
                .message(messageService.getMessage(e.getMessage(), e.getArguments()))
                .build();
    }
}
