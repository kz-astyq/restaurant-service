package kz.astyq.restaurantservice.menu_item.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItemViewResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;
    private Integer preparationTimeMinutes;
    private Long restaurantId;
    private Long categoryId;
    private String imageKey;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
