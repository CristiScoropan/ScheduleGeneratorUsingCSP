package org.example.schedulemicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.kie.api.definition.rule.All;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@Entity
@PlanningEntity
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_group_id", nullable = false)
    private ClassGroup classGroup;

    @PlanningVariable(valueRangeProviderRefs = {"timeslotRange"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeslot_id")
    private Timeslot timeslot;

    @PlanningVariable(valueRangeProviderRefs = {"classroomRange"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classoom_id")
    private Classroom classroom;
}
