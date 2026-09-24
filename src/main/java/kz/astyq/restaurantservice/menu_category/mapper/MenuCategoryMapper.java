package kz.astyq.restaurantservice.menu_category.mapper;

import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryCreateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryUpdateRequest;
import kz.astyq.restaurantservice.menu_category.models.dto.MenuCategoryViewResponse;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuCategoryMapper {
    @Mapping(target = "restaurant", ignore = true)
    MenuCategory toEntity(MenuCategoryCreateRequest dto);

    void updateEntity(MenuCategoryUpdateRequest dto, @MappingTarget MenuCategory entity);

    MenuCategoryViewResponse toViewResponse(MenuCategory entity);
}
