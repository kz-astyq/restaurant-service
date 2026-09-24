package kz.astyq.restaurantservice.menu_category.repository;

import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long> {
    boolean existsByNameAndRestaurantId(String name, Long restaurantId);

    Page<MenuCategory> findAllByRestaurantId(Pageable pageable, Long restaurantId);
}
