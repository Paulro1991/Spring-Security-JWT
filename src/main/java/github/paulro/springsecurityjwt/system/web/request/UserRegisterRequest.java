package github.paulro.springsecurityjwt.system.web.request;


import github.paulro.springsecurityjwt.system.entity.User;
import github.paulro.springsecurityjwt.system.validator.FullName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    @FullName
    @NotBlank
    private String fullName;

    public User toUser() {
        return User.builder()
                .userName(getUserName())
                .fullName(getFullName())
                .enabled(true)
                .build();
    }

}
