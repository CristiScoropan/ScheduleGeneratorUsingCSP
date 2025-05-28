package org.example.schedulemicroservice.dtos;

public record LessonDTO(
    String subject,
    String teacher,
    String classGroup,
    String timeslot,
    String classroom
){}
