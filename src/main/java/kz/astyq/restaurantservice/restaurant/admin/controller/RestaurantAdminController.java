package kz.astyq.restaurantservice.restaurant.admin.controller;

import jakarta.validation.Valid;
import kz.astyq.restaurantservice.restaurant.admin.service.RestaurantService;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantCreateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantUpdateRequest;
import kz.astyq.restaurantservice.restaurant.model.dto.RestaurantViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/restaurants")
public class RestaurantAdminController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantViewResponse createRestaurant(@Valid @RequestBody RestaurantCreateRequest request) {
        return restaurantService.create(request);
    }

    @PutMapping("/{id}")
    public RestaurantViewResponse updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantUpdateRequest request
    ) {
        return restaurantService.updateById(id, request);
    }

    @GetMapping
    public Page<RestaurantViewResponse> getAllRestaurants(Pageable pageable) {
        return restaurantService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public RestaurantViewResponse getRestaurantById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        restaurantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
