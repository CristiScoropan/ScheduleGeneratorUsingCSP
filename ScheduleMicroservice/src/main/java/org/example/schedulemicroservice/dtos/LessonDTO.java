package org.example.schedulemicroservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDTO {
    private String subject;
    private String teacher;
    private String classGroup;
    private String timeslot;
    private String classroom;
    private UserReferenceDTO userReferenceDTO;

}
