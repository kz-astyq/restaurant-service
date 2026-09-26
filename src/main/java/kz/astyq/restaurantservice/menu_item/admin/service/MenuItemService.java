package kz.astyq.restaurantservice.menu_item.admin.service;

import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemCreateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemUpdateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuItemService {
    MenuItemViewResponse createMenuItem(MenuItemCreateRequest request);

    MenuItemViewResponse updateMenuItem(Long id, MenuItemUpdateRequest request);

    void deleteMenuItem(Long id);

    MenuItemViewResponse findById(Long id);

    Page<MenuItemViewResponse> getPageByCategoryId(Long categoryId, Pageable pageable);

    void updateAvailability(Long id);
}
