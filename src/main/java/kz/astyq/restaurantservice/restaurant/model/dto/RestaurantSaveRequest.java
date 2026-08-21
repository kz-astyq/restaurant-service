package kz.astyq.restaurantservice.restaurant.model.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSaveRequest {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String status;
}
