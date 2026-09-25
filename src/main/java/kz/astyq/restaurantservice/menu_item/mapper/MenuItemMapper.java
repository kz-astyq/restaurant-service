package kz.astyq.restaurantservice.menu_item.mapper;

import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemCreateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemUpdateRequest;
import kz.astyq.restaurantservice.menu_item.model.dto.MenuItemViewResponse;
import kz.astyq.restaurantservice.menu_item.model.entity.MenuItem;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {
    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "category", ignore = true)
    MenuItem toEntity(MenuItemCreateRequest dto);

    @Mapping(target = "restaurantId", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(MenuItemUpdateRequest dto, @MappingTarget MenuItem entity);

    @Mapping(target = "categoryId", source = "category.id")
    MenuItemViewResponse toViewResponse(MenuItem entity);
}
