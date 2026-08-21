package kz.astyq.restaurantservice.restaurant.admin.service.impl;

import kz.astyq.restaurantservice.restaurant.admin.service.IRestaurantService;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantSaveRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import kz.astyq.restaurantservice.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {

    private final RestaurantRepository restaurantRepository;


    @Override
    public RestaurantViewResponse create(RestaurantSaveRequest request) {
        return null;
    }

    @Override
    public RestaurantViewResponse update(Long id, RestaurantSaveRequest request) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public RestaurantViewResponse findById(Long id) {
        return null;
    }

    @Override
    public Page<RestaurantViewResponse> getAll() {
        return null;
    }
}
