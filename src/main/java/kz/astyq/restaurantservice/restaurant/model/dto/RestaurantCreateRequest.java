package kz.astyq.restaurantservice.restaurant.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import kz.astyq.restaurantservice.opening_hours.model.dto.OpeningHourItem;
import lombok.*;

import java.util.List;

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

    @NotEmpty
    private List<@Valid OpeningHourItem> openingHours;
}
