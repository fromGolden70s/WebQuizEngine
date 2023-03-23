package engine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserNotAuthorException extends RuntimeException{
    public UserNotAuthorException() {
        super ("The user is not the author of the quiz!");
    }
}
