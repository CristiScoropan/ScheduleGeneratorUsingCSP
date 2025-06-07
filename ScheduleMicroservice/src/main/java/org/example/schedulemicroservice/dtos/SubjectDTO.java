package org.example.schedulemicroservice.dtos;

import lombok.*;
import org.example.schedulemicroservice.entities.Teacher;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String name;
    private Teacher teacher;
    private Integer frequencyPerWeek;
}
