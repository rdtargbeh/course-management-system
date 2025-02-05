package backend.school_management_system.mapper;

import backend.school_management_system.dto.InstructorDTO;
import backend.school_management_system.entity.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class InstructorMapper {
    // BeanUtils.copyProperties allows to copy properties from source object to  target object

    // Map Instructor entity to Instructor DTO entity
    public InstructorDTO fromInstructor(Instructor instructor) {
        InstructorDTO instructorDTO = new InstructorDTO();
        BeanUtils.copyProperties(instructor, instructorDTO);
        return instructorDTO;
    }

    // Map Instructor DTO entity to Instructor entity
    public Instructor fromInstructorDTO(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(instructorDTO, instructor);
        return instructor;
    }
}
