package kz.astyq.restaurantservice.opening_hours.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningHourCreateRequest {
    private List<OpeningHourItem> openingHours;
}
