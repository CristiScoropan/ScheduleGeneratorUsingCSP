package org.example.schedulemicroservice.dtos;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Long id;
    private List<LessonDTO> lessonList;
    private List<TimeslotDTO> timeslotList;
    private List<ClassroomDTO> classroomList;
}
