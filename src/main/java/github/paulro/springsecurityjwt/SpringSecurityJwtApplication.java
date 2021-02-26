package github.paulro.springsecurityjwt;

import github.paulro.springsecurityjwt.system.entity.Role;
import github.paulro.springsecurityjwt.system.entity.User;
import github.paulro.springsecurityjwt.system.entity.UserRole;
import github.paulro.springsecurityjwt.system.enums.RoleType;
import github.paulro.springsecurityjwt.system.repository.RoleRepository;
import github.paulro.springsecurityjwt.system.repository.UserRepository;
import github.paulro.springsecurityjwt.system.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityJwtApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public SpringSecurityJwtApplication(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for (RoleType roleType : RoleType.values()) {
            roleRepository.save(new Role(roleType.getName(), roleType.getDescription()));
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = User.builder().enabled(true).userName("root").fullName("admin").password(bCryptPasswordEncoder.encode("root")).build();
        userRepository.save(user);
        Role role = roleRepository.findByName(RoleType.ADMIN.getName()).get();
        userRoleRepository.save(new UserRole(user, role));
    }
}
