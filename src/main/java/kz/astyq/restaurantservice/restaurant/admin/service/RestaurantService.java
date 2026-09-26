package kz.astyq.restaurantservice.restaurant.admin.service;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantCreateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantUpdateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {
    RestaurantViewResponse create(RestaurantCreateRequest request);

    RestaurantViewResponse updateById(Long id, RestaurantUpdateRequest request);

    void deleteById(Long id);

    RestaurantViewResponse findById(Long id);

    Page<RestaurantViewResponse> getPage(Pageable pageable);

    void updateStatus(Long id);
}
