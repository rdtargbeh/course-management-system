package backend.school_management_system.repository;

import backend.school_management_system.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Page is use to enable pagination on the frontend - that means the ability scroll pages through pagination

    // Find Student by name method
    @Query(value = "select s from Student as s where  s.firstName like %:name% or s.lastName like %:name%")
    Page<Student> findStudentsByName(@Param("name") String name, PageRequest pageRequest);

    // Find Student by email method
    @Query(value = "select s from Student as s where s.user.email=:email")
    Student findStudentByEmail(@Param("email") String email);
}
