package org.example.schedulemicroservice.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassGroupDTO {
    private Long id;
    private String name;
}
