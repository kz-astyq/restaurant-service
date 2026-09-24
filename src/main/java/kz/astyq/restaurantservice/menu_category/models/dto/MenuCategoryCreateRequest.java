package kz.astyq.restaurantservice.menu_category.models.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryCreateRequest {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Long restaurantId;

    @NotNull
    @Positive
    private Integer displayOrder;
}
