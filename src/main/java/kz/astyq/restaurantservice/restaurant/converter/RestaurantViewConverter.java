package kz.astyq.restaurantservice.restaurant.converter;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantViewConverter implements Converter<Restaurant, RestaurantViewResponse> {

    @Override
    @NonNull
    public RestaurantViewResponse convert(Restaurant source) {
        return RestaurantViewResponse.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .address(source.getAddress())
                .phone(source.getPhone())
                .status(source.getStatus())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .deletedAt(source.getDeletedAt())
                .build();
    }
}
