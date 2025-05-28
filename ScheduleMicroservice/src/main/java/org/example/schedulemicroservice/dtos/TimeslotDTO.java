package org.example.schedulemicroservice.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeslotDTO {
    private Long id;
    private String dayOfWeek;
    private String time;
}
