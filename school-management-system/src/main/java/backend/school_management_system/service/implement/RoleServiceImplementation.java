package backend.school_management_system.service.implement;

import backend.school_management_system.entity.Role;
import backend.school_management_system.repository.RoleRepository;
import backend.school_management_system.service.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class RoleServiceImplementation implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImplementation(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role createRole(String roleName) {
        return roleRepository.save(new Role(roleName));
    }
}
