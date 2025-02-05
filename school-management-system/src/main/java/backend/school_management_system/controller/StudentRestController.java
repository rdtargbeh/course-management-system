package backend.school_management_system.controller;


import backend.school_management_system.dto.CourseDTO;
import backend.school_management_system.dto.StudentDTO;
import backend.school_management_system.entity.Student;
import backend.school_management_system.entity.User;
import backend.school_management_system.service.CourseService;
import backend.school_management_system.service.StudentService;
import backend.school_management_system.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/students")
public class StudentRestController {

    private StudentService studentService;
    private UserService userService;
    private CourseService courseService;

    public StudentRestController(StudentService studentService, UserService userService, CourseService courseService) {
        this.studentService = studentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    // Build A Search Students REST API
    @GetMapping
    public Page<StudentDTO> searchStudents(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size) {
        return studentService.loadStudentsByName(keyword, page, size);
    }

    // Build A Create Student REST API
    @PostMapping
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO) {
        User user = userService.loadUserByEmail(studentDTO.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email Already Exist");
        return studentService.createStudent(studentDTO);
    }

    // Build A Update Student REST API
    @PutMapping("/{studentId}")
    public StudentDTO updateStudent(@RequestBody StudentDTO studentDTO, @PathVariable Long studentId) {
        studentDTO.setStudentId(studentId);
        return studentService.updateStudent(studentDTO);
    }

    // Build A Get a Courses By Student ID REST API
    @GetMapping("/{studentId}/courses")
    public Page<CourseDTO> coursesByStudentId(@PathVariable Long studentId,
                                              @RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "size", defaultValue = "5") int size) {
        return courseService.fetchCoursesForStudent(studentId, page, size);
    }

    @GetMapping("/{studentId}/other-courses")
    public Page<CourseDTO> nonSubscribedCoursesByStudentId(@PathVariable Long studentId,
                                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                                           @RequestParam(name = "size", defaultValue = "0") int size) {
        return courseService.fetchNonEnrolledInCoursesForStudent(studentId, page, size);
    }

    @GetMapping("/find")
    public StudentDTO loadStudentByEmail(@RequestParam(name = "email", defaultValue = "") String email) {
        return studentService.loadStudentByEmail(email);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudent(@PathVariable Long studentId) {
        studentService.removeStudent(studentId);
    }

    @GetMapping("/{student_id}")
    public Student getStudentById(@PathVariable("student_id") Long studentId){
        Student student = studentService.loadStudentById(studentId);
        return student;
    }
}
