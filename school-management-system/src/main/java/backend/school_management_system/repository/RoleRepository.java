package backend.school_management_system.repository;

import backend.school_management_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    // Find Role by name method
    Role findByName(String name);
}
