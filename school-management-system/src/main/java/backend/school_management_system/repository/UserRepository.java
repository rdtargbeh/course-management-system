package backend.school_management_system.repository;

import backend.school_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find User by email method
    User findByEmail(String email);
}
