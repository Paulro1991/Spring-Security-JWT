package github.paulro.springsecurityjwt.security.service;

import github.paulro.springsecurityjwt.security.common.utils.CurrentUserUtils;
import github.paulro.springsecurityjwt.security.dto.LoginRequest;
import github.paulro.springsecurityjwt.security.entity.JwtUser;
import github.paulro.springsecurityjwt.system.entity.User;
import github.paulro.springsecurityjwt.system.service.UserService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CurrentUserUtils currentUserUtils;

    public AuthService(UserService userService, StringRedisTemplate stringRedisTemplate, CurrentUserUtils currentUserUtils) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.currentUserUtils = currentUserUtils;
    }

    public String createToken(LoginRequest loginRequest) {
        User user = userService.find(loginRequest.getUsername());
        if (!userService.check(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("The user name or password is not correct");
        }
        return null;
    }
}
