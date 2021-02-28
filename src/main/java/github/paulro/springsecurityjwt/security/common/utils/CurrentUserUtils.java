package github.paulro.springsecurityjwt.security.common.utils;

import github.paulro.springsecurityjwt.system.entity.User;
import github.paulro.springsecurityjwt.system.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserUtils {
    private final UserService userService;

    public CurrentUserUtils(UserService userService) {
        this.userService = userService;
    }

    public User getCurrentUser() {
        return userService.find(getCurrentUserName());
    }

    private String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }
}
