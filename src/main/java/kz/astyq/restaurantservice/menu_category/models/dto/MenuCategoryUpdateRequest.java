package kz.astyq.restaurantservice.menu_category.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryUpdateRequest {
    @NotBlank
    @NotNull
    private String name;

    @NotNull
    @Positive
    private Integer displayOrder;
}
