package github.paulro.springsecurityjwt.system.exception;


import javax.lang.model.type.ErrorType;
import java.util.Map;

public class UserNameNotFoundException extends BaseException {
    public UserNameNotFoundException(Map<String, Object> data) {
        super(ErrorCode.USER_NAME_NOT_FOUND, data);
    }
}
