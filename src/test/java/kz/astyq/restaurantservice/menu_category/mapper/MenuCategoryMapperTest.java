package kz.astyq.restaurantservice.menu_category.mapper;

import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class MenuCategoryMapperTest {

    private final MenuCategoryMapper mapper = Mappers.getMapper(MenuCategoryMapper.class);

    @Test
    @DisplayName("toEntity copies request fields and leaves restaurant unset")
    void shouldMapCreateRequestToEntity() {
        MenuCategoryCreateRequest request = MenuCategoryCreateRequest.builder()
                .name("Desserts")
                .restaurantId(10L)
                .displayOrder(1)
                .build();

        MenuCategory entity = mapper.toEntity(request);

        assertThat(entity.getName()).isEqualTo("Desserts");
        assertThat(entity.getDisplayOrder()).isEqualTo(1);
        assertThat(entity.getRestaurant()).isNull();
        assertThat(entity.getId()).isNull();
    }

    @Test
    @DisplayName("updateEntity overwrites editable fields and keeps identity and restaurant")
    void shouldUpdateExistingEntity() {
        Restaurant restaurant = Restaurant.builder().id(10L).build();
        MenuCategory entity = MenuCategory.builder()
                .id(1L)
                .name("Desserts")
                .displayOrder(1)
                .restaurant(restaurant)
                .restaurantId(10L)
                .build();
        MenuCategoryUpdateRequest request = MenuCategoryUpdateRequest.builder()
                .name("Drinks")
                .displayOrder(5)
                .build();

        mapper.updateEntity(request, entity);

        assertThat(entity.getName()).isEqualTo("Drinks");
        assertThat(entity.getDisplayOrder()).isEqualTo(5);
        assertThat(entity.getId()).isEqualTo(1L);
        assertThat(entity.getRestaurant()).isSameAs(restaurant);
        assertThat(entity.getRestaurantId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("toViewResponse copies all entity fields")
    void shouldMapEntityToViewResponse() {
        Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");
        Instant updatedAt = Instant.parse("2026-01-02T10:00:00Z");
        MenuCategory entity = MenuCategory.builder()
                .id(1L)
                .name("Desserts")
                .displayOrder(1)
                .restaurantId(10L)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        MenuCategoryViewResponse response = mapper.toViewResponse(entity);

        assertThat(response)
                .usingRecursiveComparison()
                .isEqualTo(MenuCategoryViewResponse.builder()
                        .id(1L)
                        .name("Desserts")
                        .displayOrder(1)
                        .restaurantId(10L)
                        .createdAt(createdAt)
                        .updatedAt(updatedAt)
                        .build());
    }
}
