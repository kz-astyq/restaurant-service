package kz.astyq.restaurantservice.menu_category.models.entity;

import jakarta.persistence.*;
import kz.astyq.restaurantservice.core.model.AuditableEntity;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "menu_category")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_menu_category",
        allocationSize = 1
)
@SQLDelete(
        sql = "update menu_category set is_deleted = true, deleted_at = now() where id = ?"
)
@SQLRestriction("is_deleted = false")
public class MenuCategory extends AuditableEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "restaurant_id", insertable = false, updatable = false)
    private Long restaurantId;
}
