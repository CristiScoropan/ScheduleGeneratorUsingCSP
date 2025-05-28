package org.example.schedulemicroservice.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDTO {
    private Long id;
    private String name;
    private Integer capacity;
}
