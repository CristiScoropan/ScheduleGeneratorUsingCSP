package org.example.schedulemicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.kie.api.definition.rule.All;

@Entity
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
