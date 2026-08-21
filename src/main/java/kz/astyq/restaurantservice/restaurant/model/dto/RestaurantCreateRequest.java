package kz.astyq.restaurantservice.restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCreateRequest {
    @NonNull
    @NotBlank
    private String name;

    private String description;

    @NonNull
    @NotBlank
    private String address;

    @NonNull
    @NotBlank
    private String phone;
}
