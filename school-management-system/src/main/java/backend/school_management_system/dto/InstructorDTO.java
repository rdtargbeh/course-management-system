package backend.school_management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstructorDTO {
    // DTO means Data Transfer Object

    private Long instructorId;
    private String firstName;
    private String lastName;
    private  String  summary;
    private UserDTO user;
}
