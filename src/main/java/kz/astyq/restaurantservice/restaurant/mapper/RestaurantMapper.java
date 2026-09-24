package kz.astyq.restaurantservice.restaurant.mapper;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantCreateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantUpdateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    Restaurant toEntity(RestaurantCreateRequest request);

    void updateEntity(RestaurantUpdateRequest request, @MappingTarget Restaurant restaurant);

    RestaurantViewResponse toViewResponse(Restaurant restaurant);
}
