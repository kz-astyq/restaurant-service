package kz.astyq.restaurantservice.menu_item.admin.service.impl;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import kz.astyq.restaurantservice.menu_category.repository.MenuCategoryRepository;
import kz.astyq.restaurantservice.menu_item.admin.service.MenuItemService;
import kz.astyq.restaurantservice.menu_item.mapper.MenuItemMapper;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemCreateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemUpdateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemViewResponse;
import kz.astyq.restaurantservice.menu_item.model.entity.MenuItem;
import kz.astyq.restaurantservice.menu_item.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static kz.astyq.restaurantservice.menu_category.utils.MessageCode.MENU_CATEGORY_NOT_FOUND;
import static kz.astyq.restaurantservice.menu_item.utils.MessageCode.MENU_ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository repository;
    private final MenuCategoryRepository categoryRepository;
    private final MenuItemMapper mapper;

    @Override
    public MenuItemViewResponse createMenuItem(MenuItemCreateRequest request) {
        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, MENU_CATEGORY_NOT_FOUND, request.getCategoryId()));
        MenuItem item = mapper.toEntity(request);
        item.setCategory(category);
        item.setRestaurantId(category.getRestaurantId());
        if (request.getIsAvailable() == null) {
            item.setIsAvailable(false);
        }
        return mapper.toViewResponse(repository.save(item));
    }

    @Override
    public MenuItemViewResponse updateMenuItem(Long id, MenuItemUpdateRequest request) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, MENU_ITEM_NOT_FOUND, id));
        MenuCategory category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, MENU_CATEGORY_NOT_FOUND, request.getCategoryId()));
        mapper.updateEntity(request, item);
        item.setCategory(category);
        return mapper.toViewResponse(repository.save(item));
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, MENU_ITEM_NOT_FOUND, id));
        repository.delete(item);
    }

    @Override
    public MenuItemViewResponse findById(Long id) {
        MenuItem item = repository.findById(id).orElseThrow(
                () -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, MENU_ITEM_NOT_FOUND, id)
        );
        return mapper.toViewResponse(item);
    }

    @Override
    public Page<MenuItemViewResponse> getPageByCategoryId(Long categoryId, Pageable pageable) {
        return repository.findAllByCategoryId(pageable, categoryId).map(mapper::toViewResponse);
    }

    @Override
    public void updateAvailability(Long id) {
        MenuItem item = repository.findById(id)
                .orElseThrow(() -> new ServiceValidationException(
                        ErrorCode.RESOURCE_NOT_FOUND, MENU_ITEM_NOT_FOUND, id));
        item.setIsAvailable(!item.getIsAvailable());
        repository.save(item);
    }
}
