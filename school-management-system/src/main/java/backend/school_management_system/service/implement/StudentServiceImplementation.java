package backend.school_management_system.service.implement;

import backend.school_management_system.dto.StudentDTO;
import backend.school_management_system.entity.Course;
import backend.school_management_system.entity.Student;
import backend.school_management_system.entity.User;
import backend.school_management_system.mapper.StudentMapper;
import backend.school_management_system.repository.StudentRepository;
import backend.school_management_system.service.StudentService;
import backend.school_management_system.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Iterator;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImplementation implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final UserService userService;

    public StudentServiceImplementation(StudentRepository studentRepository, StudentMapper studentMapper, UserService userService, CourseService courseService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.userService = userService;
    }

    // find students by ID method
    @Override
    public Student loadStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(()-> new EntityNotFoundException(
                "Student with ID " + studentId + " Not Found"));
    }

    // find students by name method
    @Override
    public Page<StudentDTO> loadStudentsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
       Page<Student> studentsPage = studentRepository.findStudentsByName(name, pageRequest);

        return new PageImpl<>(studentsPage.getContent().stream().map(student -> studentMapper.fromStudent(student))
                .collect(Collectors.toList()), pageRequest, studentsPage.getTotalElements());
    }

    // find students by email method
    @Override
    public StudentDTO loadStudentByEmail(String email) {
        return studentMapper.fromStudent(studentRepository.findStudentByEmail(email));
    }

    // create new student method
    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        User user = userService.createUser(studentDTO.getUser().getEmail(), studentDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(), "Student");
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(user);
        Student savedStudent = studentRepository.save(student);

        return studentMapper.fromStudent(savedStudent);
    }

    // update student method
    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Student loadedStudent = loadStudentById(studentDTO.getStudentId());
        Student student = studentMapper.fromStudentDTO(studentDTO);
        student.setUser(loadedStudent.getUser());
        student.setCourses(loadedStudent.getCourses());
        Student updatedStudent = studentRepository.save(student);
        return studentMapper.fromStudent(updatedStudent);
    }

    // delete student method
    @Override
    public void removeStudent(Long studentId) {
        Student student = loadStudentById(studentId);
        Iterator<Course> courseIterator = student.getCourses().iterator();
        if (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentRepository.deleteById(studentId);
    }
}
