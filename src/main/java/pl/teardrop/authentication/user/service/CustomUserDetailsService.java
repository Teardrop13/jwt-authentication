package pl.teardrop.authentication.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.teardrop.authentication.user.domain.CustomUserDetails;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.exception.InvalidEmailException;
import pl.teardrop.authentication.user.exception.UserNotFoundException;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			User user = userService.loadUserByEmail(new Email(email));
			return new CustomUserDetails(user);
		} catch (InvalidEmailException e) {
			throw new UserNotFoundException("User not found for email: %s".formatted(email));
		}
	}
}
