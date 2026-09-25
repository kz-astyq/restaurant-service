package kz.astyq.restaurantservice.restaurant.view.controller;

import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantIsOpenResponse;
import kz.astyq.restaurantservice.restaurant.view.service.RestaurantViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurants")
public class RestaurantViewController {
    private final RestaurantViewService service;

    @GetMapping("/{id}/is-open")
    public RestaurantIsOpenResponse isOpen(@PathVariable Long id) {
        return service.isOpen(id);
    }
}
