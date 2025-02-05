package backend.school_management_system.service.implement;

import backend.school_management_system.entity.Role;
import backend.school_management_system.entity.User;
import backend.school_management_system.repository.RoleRepository;
import backend.school_management_system.repository.UserRepository;
import backend.school_management_system.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImplementation(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {
        return userRepository.save(new User(email, password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleRepository.findByName(roleName);
        user.assignRoleToUser(role);

    }
}
