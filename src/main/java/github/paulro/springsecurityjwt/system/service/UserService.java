package github.paulro.springsecurityjwt.system.service;

import com.google.common.collect.ImmutableMap;
import github.paulro.springsecurityjwt.system.entity.Role;
import github.paulro.springsecurityjwt.system.entity.User;
import github.paulro.springsecurityjwt.system.entity.UserRole;
import github.paulro.springsecurityjwt.system.enums.RoleType;
import github.paulro.springsecurityjwt.system.exception.RoleNotFoundException;
import github.paulro.springsecurityjwt.system.exception.UserNameAlreadyExistException;
import github.paulro.springsecurityjwt.system.exception.UserNameNotFoundException;
import github.paulro.springsecurityjwt.system.repository.RoleRepository;
import github.paulro.springsecurityjwt.system.repository.UserRepository;
import github.paulro.springsecurityjwt.system.repository.UserRoleRepository;
import github.paulro.springsecurityjwt.system.web.representation.UserRepresentation;
import github.paulro.springsecurityjwt.system.web.request.UserRegisterRequest;
import github.paulro.springsecurityjwt.system.web.request.UserUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class UserService {

    public static final String USERNAME = "username:";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional(rollbackFor = Exception.class)
    public void save(UserRegisterRequest userRegisterRequest) {
        ensureUserNameNotExist(userRegisterRequest.getUserName());
        User user = userRegisterRequest.toUser();
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()));
        userRepository.save(user);

        // 給用戶綁定兩個角色
        Role studentRole = roleRepository.findByName(RoleType.USER.getName()).orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.USER.getName())));
        Role managerRole = roleRepository.findByName(RoleType.MANAGER.getName()).orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.MANAGER.getName())));
        userRoleRepository.save(new UserRole(user, studentRole));
        userRoleRepository.save(new UserRole(user, managerRole));
    }

    public User find(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of(USERNAME, userName)));
    }

    public void update(UserUpdateRequest userUpdateRequest) {
        User user = find(userUpdateRequest.getFullName());
        if (Objects.nonNull(userUpdateRequest.getFullName())) {
            user.setFullName(userUpdateRequest.getFullName());
        }
        if (Objects.nonNull(userUpdateRequest.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (Objects.nonNull(userUpdateRequest.getEnabled())) {
            user.setEnabled(userUpdateRequest.getEnabled());
        }
        userRepository.save(user);
    }

    public void delete(String userName) {
        if (!userRepository.existsByUserName(userName)) {
            throw new UserNameNotFoundException(ImmutableMap.of(USERNAME, userName));
        }
    }

    public Page<UserRepresentation> getAll(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize))
                .map(User::toUserRepresentation);
    }

    public boolean check(String currentPassword, String password) {
        return this.bCryptPasswordEncoder.matches(currentPassword, password);
    }

    private void ensureUserNameNotExist(String userName) {
        boolean exist = userRepository.findByUserName(userName).isPresent();
        if (exist) {
            throw new UserNameAlreadyExistException(ImmutableMap.of(USERNAME, userName));
        }
    }
}
