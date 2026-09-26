package kz.astyq.restaurantservice.menu_category.admin.service.impl;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.menu_category.mapper.MenuCategoryMapper;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import kz.astyq.restaurantservice.menu_category.repository.MenuCategoryRepository;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import kz.astyq.restaurantservice.restaurant.repository.RestaurantRepository;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static kz.astyq.restaurantservice.menu_category.utils.MessageCode.MENU_CATEGORY_ALREADY_EXISTS;
import static kz.astyq.restaurantservice.menu_category.utils.MessageCode.MENU_CATEGORY_NOT_FOUND;
import static kz.astyq.restaurantservice.restaurant.util.MessageCode.RESTAURANT_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuCategoryServiceImplTest {

    private static final Long CATEGORY_ID = 1L;
    private static final Long RESTAURANT_ID = 10L;
    private static final String CATEGORY_NAME = "Desserts";
    private static final Integer DISPLAY_ORDER = 1;

    @Mock
    private MenuCategoryRepository menuCategoryRepository;

    @Mock
    private MenuCategoryMapper menuCategoryMapper;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private MenuCategoryServiceImpl menuCategoryService;

    @Nested
    @DisplayName("createMenuCategory")
    class CreateMenuCategory {

        private final MenuCategoryCreateRequest request = MenuCategoryCreateRequest.builder()
                .name(CATEGORY_NAME)
                .restaurantId(RESTAURANT_ID)
                .displayOrder(DISPLAY_ORDER)
                .build();

        @Test
        @DisplayName("saves category linked to restaurant and returns view response")
        void shouldCreateCategory_whenNameIsUniqueAndRestaurantExists() {
            Restaurant restaurant = aRestaurant();
            MenuCategory mappedCategory = MenuCategory.builder().name(CATEGORY_NAME).displayOrder(DISPLAY_ORDER).build();
            MenuCategory savedCategory = aMenuCategory();
            MenuCategoryViewResponse expectedResponse = aViewResponse();

            when(menuCategoryRepository.existsByNameAndRestaurantId(CATEGORY_NAME, RESTAURANT_ID)).thenReturn(false);
            when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
            when(menuCategoryMapper.toEntity(request)).thenReturn(mappedCategory);
            when(menuCategoryRepository.save(mappedCategory)).thenReturn(savedCategory);
            when(menuCategoryMapper.toViewResponse(savedCategory)).thenReturn(expectedResponse);

            MenuCategoryViewResponse actualResponse = menuCategoryService.createMenuCategory(request);

            assertThat(actualResponse).isSameAs(expectedResponse);

            ArgumentCaptor<MenuCategory> captor = ArgumentCaptor.forClass(MenuCategory.class);
            verify(menuCategoryRepository).save(captor.capture());
            assertThat(captor.getValue().getRestaurant()).isSameAs(restaurant);
        }

        @Test
        @DisplayName("throws UNIQUE_RESOURCE_CONFLICT when category name already exists in restaurant")
        void shouldThrow_whenCategoryNameAlreadyExists() {
            when(menuCategoryRepository.existsByNameAndRestaurantId(CATEGORY_NAME, RESTAURANT_ID)).thenReturn(true);

            assertThatThrownBy(() -> menuCategoryService.createMenuCategory(request))
                    .isInstanceOfSatisfying(ServiceValidationException.class, exception -> {
                        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNIQUE_RESOURCE_CONFLICT);
                        assertThat(exception.getMessage()).isEqualTo(MENU_CATEGORY_ALREADY_EXISTS);
                        assertThat(exception.getArguments()).containsExactly(CATEGORY_NAME);
                    });

            verify(restaurantRepository, never()).findById(any());
            verify(menuCategoryRepository, never()).save(any());
        }

        @Test
        @DisplayName("throws RESOURCE_NOT_FOUND when restaurant does not exist")
        void shouldThrow_whenRestaurantNotFound() {
            when(menuCategoryRepository.existsByNameAndRestaurantId(CATEGORY_NAME, RESTAURANT_ID)).thenReturn(false);
            when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> menuCategoryService.createMenuCategory(request))
                    .isInstanceOfSatisfying(ServiceValidationException.class, exception -> {
                        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
                        assertThat(exception.getMessage()).isEqualTo(RESTAURANT_NOT_FOUND);
                        assertThat(exception.getArguments()).containsExactly(RESTAURANT_ID);
                    });

            verify(menuCategoryRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("updateMenuCategory")
    class UpdateMenuCategory {

        private final MenuCategoryUpdateRequest request = MenuCategoryUpdateRequest.builder()
                .name("Drinks")
                .displayOrder(2)
                .build();

        @Test
        @DisplayName("applies changes to existing category and returns view response")
        void shouldUpdateCategory_whenCategoryExists() {
            MenuCategory existingCategory = aMenuCategory();
            MenuCategoryViewResponse expectedResponse = aViewResponse();

            when(menuCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(existingCategory));
            when(menuCategoryRepository.save(existingCategory)).thenReturn(existingCategory);
            when(menuCategoryMapper.toViewResponse(existingCategory)).thenReturn(expectedResponse);

            MenuCategoryViewResponse actualResponse = menuCategoryService.updateMenuCategory(CATEGORY_ID, request);

            assertThat(actualResponse).isSameAs(expectedResponse);
            verify(menuCategoryMapper).updateEntity(request, existingCategory);
        }

        @Test
        @DisplayName("throws RESOURCE_NOT_FOUND when category does not exist")
        void shouldThrow_whenCategoryNotFound() {
            when(menuCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

            assertCategoryNotFound(() -> menuCategoryService.updateMenuCategory(CATEGORY_ID, request));

            verify(menuCategoryRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("deleteMenuCategory")
    class DeleteMenuCategory {

        @Test
        @DisplayName("deletes category when it exists")
        void shouldDeleteCategory_whenCategoryExists() {
            MenuCategory existingCategory = aMenuCategory();
            when(menuCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(existingCategory));

            menuCategoryService.deleteMenuCategory(CATEGORY_ID);

            verify(menuCategoryRepository).delete(existingCategory);
        }

        @Test
        @DisplayName("throws RESOURCE_NOT_FOUND when category does not exist")
        void shouldThrow_whenCategoryNotFound() {
            when(menuCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

            assertCategoryNotFound(() -> menuCategoryService.deleteMenuCategory(CATEGORY_ID));

            verify(menuCategoryRepository, never()).delete(any());
        }
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @DisplayName("returns view response when category exists")
        void shouldReturnCategory_whenCategoryExists() {
            MenuCategory existingCategory = aMenuCategory();
            MenuCategoryViewResponse expectedResponse = aViewResponse();

            when(menuCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(existingCategory));
            when(menuCategoryMapper.toViewResponse(existingCategory)).thenReturn(expectedResponse);

            MenuCategoryViewResponse actualResponse = menuCategoryService.findById(CATEGORY_ID);

            assertThat(actualResponse).isSameAs(expectedResponse);
        }

        @Test
        @DisplayName("throws RESOURCE_NOT_FOUND when category does not exist")
        void shouldThrow_whenCategoryNotFound() {
            when(menuCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.empty());

            assertCategoryNotFound(() -> menuCategoryService.findById(CATEGORY_ID));
        }
    }

    @Nested
    @DisplayName("getPageByRestaurantId")
    class GetPageByRestaurantId {

        private final Pageable pageable = PageRequest.of(0, 20);

        @Test
        @DisplayName("maps every category in the page to a view response")
        void shouldReturnMappedPage() {
            MenuCategory category = aMenuCategory();
            MenuCategoryViewResponse expectedResponse = aViewResponse();

            when(menuCategoryRepository.findAllByRestaurantId(pageable, RESTAURANT_ID))
                    .thenReturn(new PageImpl<>(List.of(category), pageable, 1));
            when(menuCategoryMapper.toViewResponse(category)).thenReturn(expectedResponse);

            Page<MenuCategoryViewResponse> actualPage = menuCategoryService.getPageByRestaurantId(RESTAURANT_ID, pageable);

            assertThat(actualPage.getContent()).containsExactly(expectedResponse);
            assertThat(actualPage.getTotalElements()).isEqualTo(1);
        }

        @Test
        @DisplayName("returns empty page when restaurant has no categories")
        void shouldReturnEmptyPage_whenNoCategories() {
            when(menuCategoryRepository.findAllByRestaurantId(pageable, RESTAURANT_ID))
                    .thenReturn(Page.empty(pageable));

            Page<MenuCategoryViewResponse> actualPage = menuCategoryService.getPageByRestaurantId(RESTAURANT_ID, pageable);

            assertThat(actualPage).isEmpty();
        }
    }

    private static void assertCategoryNotFound(ThrowingCallable call) {
        assertThatThrownBy(call)
                .isInstanceOfSatisfying(ServiceValidationException.class, exception -> {
                    assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.RESOURCE_NOT_FOUND);
                    assertThat(exception.getMessage()).isEqualTo(MENU_CATEGORY_NOT_FOUND);
                    assertThat(exception.getArguments()).containsExactly(CATEGORY_ID);
                });
    }

    private static Restaurant aRestaurant() {
        return Restaurant.builder().id(RESTAURANT_ID).name("Astyq").build();
    }

    private static MenuCategory aMenuCategory() {
        return MenuCategory.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .displayOrder(DISPLAY_ORDER)
                .restaurantId(RESTAURANT_ID)
                .build();
    }

    private static MenuCategoryViewResponse aViewResponse() {
        return MenuCategoryViewResponse.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .displayOrder(DISPLAY_ORDER)
                .restaurantId(RESTAURANT_ID)
                .build();
    }
}
