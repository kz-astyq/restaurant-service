package kz.astyq.restaurantservice.restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import kz.astyq.restaurantservice.restaurant.model.enums.RestaurantStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUpdateRequest {
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
    @NonNull
    @NotBlank
    private RestaurantStatus status;
}
