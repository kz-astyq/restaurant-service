package kz.astyq.restaurantservice.restaurant.admin.service.impl;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.restaurant.mapper.RestaurantMapper;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantCreateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantUpdateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import kz.astyq.restaurantservice.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static kz.astyq.restaurantservice.core.util.ErrorCode.UNIQUE_RESOURCE_CONFLICT;
import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_ALREADY_EXISTS;
import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements kz.astyq.restaurantservice.restaurant.admin.service.RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    @Override
    public RestaurantViewResponse create(RestaurantCreateRequest request) {
        if (restaurantRepository.existsByPhone(request.getPhone())) {
            throw new ServiceValidationException(UNIQUE_RESOURCE_CONFLICT, RESTAURANT_ALREADY_EXISTS, request.getPhone()
            );
        }
        Restaurant res = restaurantMapper.toEntity(request);
        return restaurantMapper.toViewResponse(restaurantRepository.save(res));
    }

    @Override
    public RestaurantViewResponse updateById(Long id, RestaurantUpdateRequest source) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id));
        restaurantMapper.updateEntity(source, restaurant);
        return restaurantMapper.toViewResponse(restaurantRepository.save(restaurant));
    }

    @Override
    public void deleteById(Long id) {
        Restaurant res = restaurantRepository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id));
        restaurantRepository.delete(res);
    }

    @Override
    public RestaurantViewResponse findById(Long id) {
        Restaurant res = restaurantRepository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id));
        return restaurantMapper.toViewResponse(res);
    }

    @Override
    public Page<RestaurantViewResponse> getPage(Pageable pageable) {
        return restaurantRepository.findAll(pageable).map(restaurantMapper::toViewResponse);
    }
}
