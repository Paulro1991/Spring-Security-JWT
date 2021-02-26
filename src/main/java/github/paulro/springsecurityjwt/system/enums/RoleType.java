package github.paulro.springsecurityjwt.system.enums;

import lombok.Getter;

@Getter
public enum RoleType {
    USER("USER", "用戶"),
    TEMP_USER("TEMP_USER", "臨時用戶"),
    MANAGER("MANAGER", "管理者"),
    ADMIN("ADMIN", "Admin"),
    ;

    private String name;
    private String description;

    RoleType(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
