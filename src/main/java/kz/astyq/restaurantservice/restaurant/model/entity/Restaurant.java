package kz.astyq.restaurantservice.restaurant.model.entity;

import jakarta.persistence.*;
import kz.astyq.restaurantservice.core.model.AuditableEntity;
import kz.astyq.restaurantservice.menu_category.models.entity.MenuCategory;
import kz.astyq.restaurantservice.opening_hours.model.entity.OpeningHour;
import kz.astyq.restaurantservice.restaurant.model.enums.RestaurantStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(
        name = "seq",
        sequenceName = "s_restaurant",
        allocationSize = 1
)
@SQLDelete(
        sql = "update restaurants set is_deleted = true, deleted_at = now() where id = ?"
)
@SQLRestriction("is_deleted = false")
public class Restaurant extends AuditableEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 30)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RestaurantStatus status;

    @Builder.Default
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuCategory> categories = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OpeningHour> openingHours = new ArrayList<>();
}
