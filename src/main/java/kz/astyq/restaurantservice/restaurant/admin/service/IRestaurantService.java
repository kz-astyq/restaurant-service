package kz.astyq.restaurantservice.restaurant.admin.service;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantSaveRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import org.springframework.data.domain.Page;

public interface IRestaurantService {
    RestaurantViewResponse create(RestaurantSaveRequest request);

    RestaurantViewResponse update(Long id, RestaurantSaveRequest request);

    void deleteById(Long id);

    RestaurantViewResponse findById(Long id);

    Page<RestaurantViewResponse> getAll();
}
