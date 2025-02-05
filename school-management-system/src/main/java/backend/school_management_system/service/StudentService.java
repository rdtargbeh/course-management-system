package backend.school_management_system.service;

import backend.school_management_system.dto.StudentDTO;
import backend.school_management_system.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {

    Student loadStudentById(Long studentId);

    Page<StudentDTO> loadStudentsByName(String name, int page, int size);

    StudentDTO loadStudentByEmail(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    StudentDTO updateStudent(StudentDTO studentDTO);

    void removeStudent(Long studentId);

}
