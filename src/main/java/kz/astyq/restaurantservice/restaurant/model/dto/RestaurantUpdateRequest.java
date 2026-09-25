package kz.astyq.restaurantservice.restaurant.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kz.astyq.restaurantservice.opening_hours.model.dto.OpeningHourItem;
import kz.astyq.restaurantservice.restaurant.model.enums.RestaurantStatus;
import lombok.*;

import java.util.List;

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
    private RestaurantStatus status;

    @NotEmpty
    private List<@Valid OpeningHourItem> openingHours;
}
