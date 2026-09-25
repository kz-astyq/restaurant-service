package kz.astyq.restaurantservice.opening_hours.repository;

import kz.astyq.restaurantservice.opening_hours.model.entity.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpeningHourRepository extends JpaRepository<OpeningHour, Long> {
}
