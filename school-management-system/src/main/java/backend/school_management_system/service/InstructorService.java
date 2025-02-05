package backend.school_management_system.service;

import backend.school_management_system.dto.InstructorDTO;
import backend.school_management_system.entity.Instructor;
import org.springframework.data.domain.Page;


import java.util.List;

public interface InstructorService {
    Instructor loadInstructorById(Long instructorId);

    Instructor findInstructorById(Long instructorId);

    Page<InstructorDTO> findInstructorsByName(String name, int page, int size);

    InstructorDTO loadInstructorByEmail(String email);

    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    InstructorDTO updateInstructor(InstructorDTO instructorDTO);

    List<InstructorDTO> fetchInstructors();

    void removeInstructor(Long instructorId);

}
