package kz.astyq.restaurantservice.menu_category.admin.controller;

import kz.astyq.restaurantservice.core.exception.ServiceValidationException;
import kz.astyq.restaurantservice.core.i18n.MessageService;
import kz.astyq.restaurantservice.core.util.ErrorCode;
import kz.astyq.restaurantservice.menu_category.admin.service.MenuCategoryService;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import kz.astyq.restaurantservice.menu_item.admin.service.MenuItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static kz.astyq.restaurantservice.menu_category.utils.MessageCode.MENU_CATEGORY_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuCategoryAdminController.class)
class MenuCategoryAdminControllerTest {

    private static final String BASE_URL = "/menu-categories";
    private static final Long CATEGORY_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuCategoryService menuCategoryService;

    @MockitoBean
    private MenuItemService menuItemService;

    @MockitoBean
    private MessageService messageService;

    @Nested
    @DisplayName("POST /menu-categories")
    class CreateMenuCategory {

        @Test
        @DisplayName("returns 200 with created category for valid request")
        void shouldReturnCreatedCategory() throws Exception {
            when(menuCategoryService.createMenuCategory(any(MenuCategoryCreateRequest.class)))
                    .thenReturn(aViewResponse());

            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"name": "Desserts", "restaurantId": 10, "displayOrder": 1}
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(CATEGORY_ID))
                    .andExpect(jsonPath("$.name").value("Desserts"))
                    .andExpect(jsonPath("$.restaurantId").value(10));

            ArgumentCaptor<MenuCategoryCreateRequest> captor = ArgumentCaptor.forClass(MenuCategoryCreateRequest.class);
            verify(menuCategoryService).createMenuCategory(captor.capture());
            assertThat(captor.getValue().getName()).isEqualTo("Desserts");
            assertThat(captor.getValue().getRestaurantId()).isEqualTo(10L);
            assertThat(captor.getValue().getDisplayOrder()).isEqualTo(1);
        }

        @Test
        @DisplayName("returns 400 and skips service when request is invalid")
        void shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
            mockMvc.perform(post(BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"name": "", "restaurantId": -1, "displayOrder": null}
                                    """))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(menuCategoryService);
        }
    }

    @Nested
    @DisplayName("PUT /menu-categories/{id}")
    class UpdateMenuCategory {

        @Test
        @DisplayName("returns 200 with updated category for valid request")
        void shouldReturnUpdatedCategory() throws Exception {
            when(menuCategoryService.updateMenuCategory(eq(CATEGORY_ID), any(MenuCategoryUpdateRequest.class)))
                    .thenReturn(aViewResponse());

            mockMvc.perform(put(BASE_URL + "/{id}", CATEGORY_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"name": "Desserts", "displayOrder": 1}
                                    """))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(CATEGORY_ID));
        }

        @Test
        @DisplayName("returns 400 when display order is not positive")
        void shouldReturnBadRequest_whenDisplayOrderIsNotPositive() throws Exception {
            mockMvc.perform(put(BASE_URL + "/{id}", CATEGORY_ID)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"name": "Desserts", "displayOrder": 0}
                                    """))
                    .andExpect(status().isBadRequest());

            verifyNoInteractions(menuCategoryService);
        }
    }

    @Nested
    @DisplayName("DELETE /menu-categories/{id}")
    class DeleteMenuCategory {

        @Test
        @DisplayName("returns 204 and delegates to service")
        void shouldReturnNoContent() throws Exception {
            mockMvc.perform(delete(BASE_URL + "/{id}", CATEGORY_ID))
                    .andExpect(status().isNoContent());

            verify(menuCategoryService).deleteMenuCategory(CATEGORY_ID);
        }
    }

    @Nested
    @DisplayName("GET /menu-categories/{id}")
    class GetMenuCategory {

        @Test
        @DisplayName("returns 200 with category when it exists")
        void shouldReturnCategory() throws Exception {
            when(menuCategoryService.findById(CATEGORY_ID)).thenReturn(aViewResponse());

            mockMvc.perform(get(BASE_URL + "/{id}", CATEGORY_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(CATEGORY_ID))
                    .andExpect(jsonPath("$.name").value("Desserts"))
                    .andExpect(jsonPath("$.displayOrder").value(1));
        }

        @Test
        @DisplayName("returns 400 with error body when category is not found")
        void shouldReturnErrorResponse_whenCategoryNotFound() throws Exception {
            when(menuCategoryService.findById(CATEGORY_ID)).thenThrow(
                    new ServiceValidationException(ErrorCode.RESOURCE_NOT_FOUND, MENU_CATEGORY_NOT_FOUND, CATEGORY_ID));
            when(messageService.getMessage(eq(MENU_CATEGORY_NOT_FOUND), any(Object[].class)))
                    .thenReturn("Menu category not found");

            mockMvc.perform(get(BASE_URL + "/{id}", CATEGORY_ID))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(ErrorCode.RESOURCE_NOT_FOUND.name()))
                    .andExpect(jsonPath("$.message").value("Menu category not found"));
        }
    }

    private static MenuCategoryViewResponse aViewResponse() {
        return MenuCategoryViewResponse.builder()
                .id(CATEGORY_ID)
                .name("Desserts")
                .displayOrder(1)
                .restaurantId(10L)
                .build();
    }
}
