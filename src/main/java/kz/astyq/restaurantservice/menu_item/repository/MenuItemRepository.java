package kz.astyq.restaurantservice.menu_item.repository;

import kz.astyq.restaurantservice.menu_item.model.entity.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Page<MenuItem> findAllByCategoryId(Pageable pageable, Long categoryId);
}
