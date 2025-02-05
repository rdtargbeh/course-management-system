package backend.school_management_system.mapper;

import backend.school_management_system.dto.CourseDTO;
import backend.school_management_system.entity.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class CourseMapper {
    private InstructorMapper instructorMapper;

    public CourseMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }

    // BeanUtils.copyProperties allows to copy properties from source object to  target object
    // Map Course entity to DTO entity
    public CourseDTO fromCourse(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        courseDTO.setInstructor(instructorMapper.fromInstructor(course.getInstructor()));
        return courseDTO;
    }

    // Map DTO entity to Course entity
    public Course fromCourseDTO(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        return course;
    }

}
