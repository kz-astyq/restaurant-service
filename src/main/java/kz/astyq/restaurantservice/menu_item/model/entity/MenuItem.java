package kz.astyq.restaurantservice.menu_item.model.entity;

import jakarta.persistence.*;
import kz.astyq.restaurantservice.core.model.AuditableEntity;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq", sequenceName = "s_menu_items", allocationSize = 1)
@SQLDelete(sql = "update menu_items set is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted = false")
public class MenuItem extends AuditableEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "image_key", nullable = false)
    private String imageKey;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "preparation_time_minutes", nullable = false)
    private Integer preparationTimeMinutes;

    @Column(name = "restaurant_id", updatable = false)
    private Long restaurantId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private MenuCategory category;
}
