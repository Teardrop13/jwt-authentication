package pl.teardrop.authentication.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.teardrop.authentication.user.exception.UserNotFoundException;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;

@NoArgsConstructor(access = AccessLevel.NONE)
public class UserUtils {

	public static User currentUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

		if (authentication == null
			|| authentication.getPrincipal() == null
			|| !(authentication.getPrincipal() instanceof User)) {
			throw new UserNotFoundException("User not found.");
		}

		Object principal = authentication.getPrincipal();
		return (User) principal;
	}

	public static UserId currentUserId() {
		return currentUser().userId();
	}
}
