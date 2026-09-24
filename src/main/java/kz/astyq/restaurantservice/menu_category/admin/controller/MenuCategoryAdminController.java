package kz.astyq.restaurantservice.menu_category.admin.controller;

import jakarta.validation.Valid;
import kz.astyq.restaurantservice.menu_category.admin.service.MenuCategoryService;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu-categories")
@RequiredArgsConstructor
public class MenuCategoryAdminController {
    private final MenuCategoryService menuCategoryService;

    @PostMapping
    public MenuCategoryViewResponse createMenuCategory(@Valid @RequestBody MenuCategoryCreateRequest request) {
        return menuCategoryService.createMenuCategory(request);
    }

    @PutMapping("/{id}")
    public MenuCategoryViewResponse updateMenuCategory(@PathVariable Long id, @Valid @RequestBody MenuCategoryUpdateRequest request) {
        return menuCategoryService.updateMenuCategory(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuCategory(@PathVariable Long id) {
        menuCategoryService.deleteMenuCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public MenuCategoryViewResponse getMenuCategory(@PathVariable Long id) {
        return menuCategoryService.findById(id);
    }
}
