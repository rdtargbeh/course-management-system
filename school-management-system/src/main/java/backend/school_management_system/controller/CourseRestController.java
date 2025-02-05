package backend.school_management_system.controller;

import backend.school_management_system.dto.CourseDTO;
import backend.school_management_system.entity.Course;
import backend.school_management_system.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin("*")
@RestController
@RequestMapping("/courses")
public class CourseRestController {
    private CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Page<CourseDTO> searchCourses(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(name = "page", defaultValue = "1") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size) {
        return courseService.findCoursesByCourseName(keyword, page, size);
    }

    @PostMapping
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO) {
        return courseService.createCourse(courseDTO);
    }

    @PutMapping("/{courseId}")
    public CourseDTO updateCourse(@RequestBody CourseDTO courseDTO, @PathVariable Long courseId) {
        courseDTO.setCourseId(courseId);
        return courseService.updateCourse(courseDTO);
    }

    @PostMapping("/{courseId}/enroll/students/{studentId}")
    public void enrollStudentInCourse(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        courseService.assignStudentToCourse(courseId, studentId);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course>  getCourseById(@PathVariable("courseId") Long courseId){
        Course course= courseService.loadCourseById(courseId);
        return ResponseEntity.ok(course);

    }


    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseService.removeCourse(courseId);
    }

}
