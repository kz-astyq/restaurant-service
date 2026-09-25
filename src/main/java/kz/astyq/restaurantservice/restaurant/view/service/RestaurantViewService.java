package kz.astyq.restaurantservice.restaurant.view.service;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantIsOpenResponse;

public interface RestaurantViewService {
    RestaurantIsOpenResponse isOpen(Long id);
}
