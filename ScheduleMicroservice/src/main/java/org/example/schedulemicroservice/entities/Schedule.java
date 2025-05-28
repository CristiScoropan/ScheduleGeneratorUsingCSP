package org.example.schedulemicroservice.entities;

import jakarta.persistence.*;
import lombok.*;
import org.kie.api.definition.rule.All;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@Entity
@PlanningSolution
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "schedules")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    @PlanningEntityCollectionProperty
    private List<Lesson> lessonList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    @ValueRangeProvider(id = "timeslotRange")
    private List<Timeslot> timeslotList;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    @ValueRangeProvider(id = "classroomRange")
    private List<Classroom> classroomList;

    @PlanningScore
    private HardSoftScore score;
}
