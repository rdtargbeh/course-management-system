package backend.school_management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDTO {
    // DTO means Data Transfer Object

    private Long studentId;
    private String firstName;
    private String lastName;
    private String level;

    private UserDTO user;
}
