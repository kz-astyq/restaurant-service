package kz.astyq.restaurantservice.menu_category.models.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuCategoryViewResponse {
    private Long id;
    private String name;
    private Integer displayOrder;
    private Long restaurantId;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
