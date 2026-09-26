package kz.astyq.restaurantservice.menu_item.admin.controller;

import kz.astyq.restaurantservice.menu_item.admin.service.MenuItemService;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemCreateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemUpdateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemViewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/menu-items")
@RequiredArgsConstructor
public class MenuItemAdminController {
    private final MenuItemService service;

    @PostMapping
    public MenuItemViewResponse createMenuItem(@Valid @RequestBody MenuItemCreateRequest request) {
        return service.createMenuItem(request);
    }

    @PutMapping("/{id}")
    public MenuItemViewResponse updateMenuItem(@PathVariable Long id, @Valid @RequestBody MenuItemUpdateRequest request) {
        return service.updateMenuItem(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        service.deleteMenuItem(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public MenuItemViewResponse getMenuItem(@PathVariable Long id) {
        return service.findById(id);
    }

    @PatchMapping("/{id}/availability")
    public void updateAvailability(@PathVariable Long id) {
        service.updateAvailability(id);
    }
}
