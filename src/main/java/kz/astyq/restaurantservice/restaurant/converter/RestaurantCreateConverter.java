package kz.astyq.restaurantservice.restaurant.converter;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantCreateRequest;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import kz.astyq.restaurantservice.restaurant.model.enums.RestaurantStatus;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;


@Component
@RequiredArgsConstructor
public class RestaurantCreateConverter implements Converter<RestaurantCreateRequest, Restaurant> {
    @Override
    @NonNull
    public Restaurant convert(RestaurantCreateRequest source) {
        return Restaurant.builder()
                .name(source.getName())
                .description(source.getDescription())
                .address(source.getAddress())
                .phone(source.getPhone())
                .status(RestaurantStatus.ACTIVE)
                .build();
    }
}
