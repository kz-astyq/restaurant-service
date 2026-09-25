package kz.astyq.restaurantservice.opening_hours.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningHourItem {
    @NotNull
    private DayOfWeek dayOfWeek;
    @NotNull
    private LocalTime openTime;
    @NotNull
    private LocalTime closeTime;
}
