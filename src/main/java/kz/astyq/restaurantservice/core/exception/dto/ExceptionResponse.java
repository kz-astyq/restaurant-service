package kz.astyq.restaurantservice.core.exception.dto;

import kz.astyq.restaurantservice.core.util.ErrorCode;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private String status;
    private ErrorCode code;
    private String message;
}
