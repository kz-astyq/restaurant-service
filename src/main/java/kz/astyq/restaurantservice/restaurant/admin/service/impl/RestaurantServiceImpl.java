package kz.astyq.restaurantservice.restaurant.admin.service.impl;

import jakarta.transaction.Transactional;
import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.opening_hours.model.entity.OpeningHour;
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

import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kz.astyq.restaurantservice.core.util.ErrorCode.UNIQUE_RESOURCE_CONFLICT;
import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_ALREADY_EXISTS;
import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements kz.astyq.restaurantservice.restaurant.admin.service.RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    @Override
    @Transactional
    public RestaurantViewResponse create(RestaurantCreateRequest request) {
        if (repository.existsByPhone(request.getPhone())) {
            throw new ServiceValidationException(UNIQUE_RESOURCE_CONFLICT, RESTAURANT_ALREADY_EXISTS, request.getPhone()
            );
        }
        Restaurant res = restaurantMapper.toEntity(request);
        validateNoOverlaps(res.getOpeningHours());
        return restaurantMapper.toViewResponse(repository.save(res));
    }

    @Override
    @Transactional
    public RestaurantViewResponse updateById(Long id, RestaurantUpdateRequest source) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id));
        restaurantMapper.updateEntity(source, restaurant);
        validateNoOverlaps(restaurant.getOpeningHours());
        return restaurantMapper.toViewResponse(repository.save(restaurant));
    }

    @Override
    public void deleteById(Long id) {
        Restaurant res = repository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id));
        repository.delete(res);
    }

    @Override
    public RestaurantViewResponse findById(Long id) {
        Restaurant res = repository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id));
        return restaurantMapper.toViewResponse(res);
    }

    @Override
    public Page<RestaurantViewResponse> getPage(Pageable pageable) {
        return repository.findAll(pageable).map(restaurantMapper::toViewResponse);
    }

    private void validateNoOverlaps(List<OpeningHour> hours) {
        Map<DayOfWeek, List<OpeningHour>> byDay = hours.stream()
                .collect(Collectors.groupingBy(OpeningHour::getDayOfWeek));

        byDay.forEach((day, list) -> {
            list.sort(Comparator.comparing(OpeningHour::getOpenTime));
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getOpenTime().isBefore(list.get(i - 1).getCloseTime())) {
                    throw new IllegalArgumentException("Overlapping intervals on " + day);
                }
            }
        });
    }
}
