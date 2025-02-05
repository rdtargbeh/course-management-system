package backend.school_management_system.controller;

import backend.school_management_system.dto.CourseDTO;
import backend.school_management_system.dto.InstructorDTO;
import backend.school_management_system.entity.Instructor;
import backend.school_management_system.entity.User;
import backend.school_management_system.service.CourseService;
import backend.school_management_system.service.InstructorService;
import backend.school_management_system.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/instructors")
public class InstructorRestController {

    private InstructorService instructorService;

    private UserService userService;

    private CourseService courseService;

    public InstructorRestController(InstructorService instructorService, UserService userService, CourseService courseService) {
        this.instructorService = instructorService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public Page<InstructorDTO> searchInstructors(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                 @RequestParam(name = "page", defaultValue = "1") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size) {
        return instructorService.findInstructorsByName(keyword, page, size);
    }

    @GetMapping("/all")
    public List<InstructorDTO> findAllInstructors() {
        return instructorService.fetchInstructors();
    }

    @PostMapping
    public InstructorDTO saveInstructor(@RequestBody InstructorDTO instructorDTO) {
        User user = userService.loadUserByEmail(instructorDTO.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email Already Exist");
        return instructorService.createInstructor(instructorDTO);
    }

    @PutMapping("/{instructorId}")
    public InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDTO, @PathVariable Long instructorId) {
        instructorDTO.setInstructorId(instructorId);
        return instructorService.updateInstructor(instructorDTO);
    }

    @GetMapping("/{instructorId}/courses")
    public Page<CourseDTO> coursesByInstructorId(@PathVariable Long instructorId,
                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                 @RequestParam(name = "size", defaultValue = "5") int size) {
        return courseService.fetchCoursesForInstructor(instructorId, page, size);
    }

    @GetMapping("/find")
    public InstructorDTO loadInstructorByEmail(@RequestParam(name = "email", defaultValue = "") String email) {
        return instructorService.loadInstructorByEmail(email);
    }

    @GetMapping("/find/{instructorId}")
    public ResponseEntity<Instructor> getCourseById(@PathVariable("instructorId") Long instructorId){
        Instructor instructor= instructorService.findInstructorById(instructorId);
        return ResponseEntity.ok(instructor);
    }

    @DeleteMapping("/{instructorId}")
    public void deleteInstructor(@PathVariable Long instructorId) {
        instructorService.removeInstructor(instructorId);
    }

}
