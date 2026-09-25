package kz.astyq.restaurantservice.restaurant.view.service.impl;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.opening_hours.model.entity.OpeningHour;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantIsOpenResponse;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import kz.astyq.restaurantservice.restaurant.repository.RestaurantRepository;
import kz.astyq.restaurantservice.restaurant.view.service.RestaurantViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;

import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RestaurantViewServiceImpl implements RestaurantViewService {
    private final RestaurantRepository repository;

    @Override
    public RestaurantIsOpenResponse isOpen(Long id) {
        Restaurant restaurant = repository.findById(id).orElseThrow(
                () -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, id)
        );

        if (isOpen(restaurant.getOpeningHours(), ZonedDateTime.now())) {
            return RestaurantIsOpenResponse.builder()
                    .isOpen(true)
                    .build();
        }

        return RestaurantIsOpenResponse.builder()
                .isOpen(false)
                .build();
    }

    private boolean isOpen(List<OpeningHour> hours, ZonedDateTime now) {
        DayOfWeek today = now.getDayOfWeek();
        LocalTime time = now.toLocalTime();

        for (OpeningHour h : hours) {
            boolean overnight = h.getCloseTime().isBefore(h.getOpenTime());

            if (h.getDayOfWeek() == today) {
                if (!overnight && !time.isBefore(h.getOpenTime()) && time.isBefore(h.getCloseTime())) return true;
                if (overnight && !time.isBefore(h.getOpenTime())) return true;
            }
            if (overnight && h.getDayOfWeek() == today.minus(1) && time.isBefore(h.getCloseTime())) {
                return true;
            }
        }
        return false;
    }
}
