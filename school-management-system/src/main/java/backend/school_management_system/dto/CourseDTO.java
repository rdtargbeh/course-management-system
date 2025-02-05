package backend.school_management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    // DTO means Data Transfer Object

    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String courseDescription;
    private InstructorDTO instructor;
}
