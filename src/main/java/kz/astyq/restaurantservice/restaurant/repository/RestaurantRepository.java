package kz.astyq.restaurantservice.restaurant.repository;

import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
