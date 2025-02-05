package backend.school_management_system.repository;

import backend.school_management_system.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {

    // Page is use to enable pagination on the frontend - that means the ability scroll pages through pagination

    // find courses by using keywords method
    Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);

    // Get the courses student enrolled in method
    @Query(value = "select * from courses as c where c.course_id in " +
            "(select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
    Page<Course> getCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);

    // Get courses that student Not Enrolled In
    @Query(value = "select * from courses as c where c.course_id not in " +
            "(select e.course_id from enrolled_in as e where e.student_id=:studentId)", nativeQuery = true)
    Page<Course> getNonEnrolledInCoursesByStudentId(@Param("studentId") Long studentId, Pageable pageable);

    // Get course for Instructor by instructor ID
    @Query(value = "select c from Course as c where c.instructor.instructorId=:instructorId")
    Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);
}
