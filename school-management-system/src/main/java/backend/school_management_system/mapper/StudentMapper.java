package backend.school_management_system.mapper;

import backend.school_management_system.dto.StudentDTO;
import backend.school_management_system.entity.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class StudentMapper {
    // BeanUtils.copyProperties allows to copy properties from source object to  target object

    // Map Student entity to Student DTO entity
    public StudentDTO fromStudent(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        return studentDTO;
    }

    // Map Student DTO entity to Student entity
    public Student fromStudentDTO(StudentDTO studentDTO) {
        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        return student;
    }

}
