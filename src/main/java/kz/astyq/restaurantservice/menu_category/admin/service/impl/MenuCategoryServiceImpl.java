package kz.astyq.restaurantservice.menu_category.admin.service.impl;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.menu_category.admin.service.MenuCategoryService;
import kz.astyq.restaurantservice.menu_category.mapper.MenuCategoryMapper;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import kz.astyq.restaurantservice.menu_category.repository.MenuCategoryRepository;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import kz.astyq.restaurantservice.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static kz.astyq.restaurantservice.menu_category.utils.MessageCode.MENU_CATEGORY_ALREADY_EXISTS;
import static kz.astyq.restaurantservice.menu_category.utils.MessageCode.MENU_CATEGORY_NOT_FOUND;
import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {
    private final MenuCategoryRepository menuCategoryRepository;
    private final MenuCategoryMapper menuCategoryMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    public MenuCategoryViewResponse createMenuCategory(MenuCategoryCreateRequest request) {
        if (menuCategoryRepository.existsByNameAndRestaurantId(request.getName(), request.getRestaurantId())) {
            throw new ServiceValidationException(ErrorCode.UNIQUE_RESOURCE_CONFLICT, MENU_CATEGORY_ALREADY_EXISTS, request.getName());
        }
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, RESTAURANT_NOT_FOUND, request.getRestaurantId()));
        MenuCategory category = menuCategoryMapper.toEntity(request);
        category.setRestaurant(restaurant);

        return menuCategoryMapper.toViewResponse(menuCategoryRepository.save(category));
    }

    @Override
    public MenuCategoryViewResponse updateMenuCategory(Long id, MenuCategoryUpdateRequest request) {
        MenuCategory menuCategory = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, MENU_CATEGORY_NOT_FOUND, id));
        menuCategoryMapper.updateEntity(request, menuCategory);
        return menuCategoryMapper.toViewResponse(menuCategoryRepository.save(menuCategory));
    }

    @Override
    public void deleteMenuCategory(Long id) {
        MenuCategory category = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, MENU_CATEGORY_NOT_FOUND, id));
        menuCategoryRepository.delete(category);
    }

    @Override
    public MenuCategoryViewResponse findById(Long id) {
        MenuCategory category = menuCategoryRepository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, MENU_CATEGORY_NOT_FOUND, id));
        return menuCategoryMapper.toViewResponse(category);
    }

    @Override
    public Page<MenuCategoryViewResponse> getPageByRestaurantId(Long restaurantId, Pageable pageable) {
        return menuCategoryRepository.findAllByRestaurantId(pageable, restaurantId).map(menuCategoryMapper::toViewResponse);
    }
}
