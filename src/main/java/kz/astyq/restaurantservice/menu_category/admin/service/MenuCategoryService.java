package kz.astyq.restaurantservice.menu_category.admin.service;

import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuCategoryService {
    MenuCategoryViewResponse createMenuCategory(MenuCategoryCreateRequest request);

    MenuCategoryViewResponse updateMenuCategory(Long id, MenuCategoryUpdateRequest request);

    void deleteMenuCategory(Long id);

    MenuCategoryViewResponse findById(Long id);

    Page<MenuCategoryViewResponse> getPageByRestaurantId(Long restaurantId, Pageable pageable);
}
