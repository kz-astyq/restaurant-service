package kz.astyq.restaurantservice.restaurant.model.dto;

import kz.astyq.restaurantservice.restaurant.model.enums.RestaurantStatus;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantViewResponse {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private RestaurantStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
