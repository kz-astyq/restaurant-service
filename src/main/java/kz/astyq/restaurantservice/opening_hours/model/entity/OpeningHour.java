package kz.astyq.restaurantservice.opening_hours.model.entity;

import jakarta.persistence.*;
import kz.astyq.restaurantservice.core.model.BaseEntity;
import kz.astyq.restaurantservice.restaurant.model.entity.Restaurant;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "opening_hours")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq", sequenceName = "s_opening_hours", allocationSize = 1)
public class OpeningHour extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}
