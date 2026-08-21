package kz.astyq.restaurantservice.restaurant.converter;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantUpdateRequest;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantUpdateConverter {
    public Restaurant convert(Long id, RestaurantUpdateRequest source) {
        return Restaurant.builder()
                .id(id)
                .name(source.getName())
                .description(source.getDescription())
                .address(source.getAddress())
                .phone(source.getPhone())
                .status(source.getStatus())
                .build();
    }


}
