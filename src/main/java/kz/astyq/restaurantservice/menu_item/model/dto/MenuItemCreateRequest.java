package kz.astyq.restaurantservice.menu_item.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemCreateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotBlank
    private String imageKey;


    @NotNull
    @Positive
    private Integer preparationTimeMinutes;

    private Boolean isAvailable;

    @NotNull
    @Positive
    private Long categoryId;
}
