package kz.astyq.restaurantservice.restaurant.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantIsOpenResponse {
    private boolean isOpen;
}
