package kz.astyq.restaurantservice.restaurant.mapper;

import kz.astyq.restaurantservice.opening_hours.model.dto.OpeningHourItem;
import kz.astyq.restaurantservice.opening_hours.model.entity.OpeningHour;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantCreateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantUpdateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    Restaurant toEntity(RestaurantCreateRequest request);

    @Mapping(target = "openingHours", ignore = true)
    void updateEntity(RestaurantUpdateRequest request, @MappingTarget Restaurant restaurant);

    RestaurantViewResponse toViewResponse(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    OpeningHour toOpeningHour(OpeningHourItem item);

    List<OpeningHour> toOpeningHours(List<OpeningHourItem> items);

    @AfterMapping
    default void linkOpeningHours(RestaurantCreateRequest request, @MappingTarget Restaurant restaurant) {
        restaurant.getOpeningHours().forEach(hour -> hour.setRestaurant(restaurant));
    }

    @AfterMapping
    default void replaceOpeningHours(RestaurantUpdateRequest request, @MappingTarget Restaurant restaurant) {
        restaurant.getOpeningHours().clear();
        toOpeningHours(request.getOpeningHours()).forEach(hour -> {
            hour.setRestaurant(restaurant);
            restaurant.getOpeningHours().add(hour);
        });
    }
}
