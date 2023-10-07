package pl.teardrop.authentication.user.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
		value = HttpStatus.NOT_FOUND,
		reason = "Requested user does not exist"
)
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}
}
