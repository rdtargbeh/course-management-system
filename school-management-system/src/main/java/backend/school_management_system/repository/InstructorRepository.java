package backend.school_management_system.repository;

import backend.school_management_system.entity.Instructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    // Find Instructor by name method
    @Query(value = "select i  from Instructor as i where i.firstName like %:name% or i.lastName like %:name%")
    Page<Instructor> findInstructorsByName(@Param("name") String name, PageRequest pageRequest);

    // Find Instructor by email method
    @Query(value = "select i from Instructor as i where i.user.email=:email")
    Instructor findInstructorByEmail(@Param("email") String email);

    // Find Instructor by ID method
    @Query(value = "select i from Instructor as i  where i.instructorId=:instructorId")
    Instructor findInstructorById(@Param("instructorId") Long instructorId);
}
