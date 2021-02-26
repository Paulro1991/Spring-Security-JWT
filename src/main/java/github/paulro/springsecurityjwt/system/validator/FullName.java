package github.paulro.springsecurityjwt.system.validator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FullNameValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FullName {
    String message() default "姓名格式錯誤";

    Class[] groups() default {};
    Class[] payload() default {};
}
