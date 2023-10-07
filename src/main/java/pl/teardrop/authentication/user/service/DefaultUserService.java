package pl.teardrop.authentication.user.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import pl.teardrop.authentication.user.exception.UserNotFoundException;
import pl.teardrop.authentication.user.repository.UserRepository;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.Password;
import pl.teardrop.authentication.user.domain.PasswordEncrypted;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

	private final UserRepository userRepository;

	@Lazy
	private final PasswordEncryptor passwordEncryptor;

	@Override
	public Optional<User> getById(UserId userId) {
		return userRepository.findById(userId.getId());
	}

	@Override
	public boolean checkIfUserExists(@NonNull Email email) {
		try {
			loadUserByEmail(email);
			return true;
		} catch (UserNotFoundException e) {
			log.info("User not found for email: {}", email);
		}

		return false;
	}

	@Override
	public User loadUserByEmail(Email email) throws UserNotFoundException {
		return userRepository.findUserByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Failed to find user with email: " + email));
	}

	@Override
	public User create(Email email, Password password) {
		PasswordEncrypted passwordEncrypted = passwordEncryptor.encrypt(password);

		User user = User.builder()
				.passwordEncrypted(passwordEncrypted)
				.email(email)
				.build();

		User userAdded = save(user);
		log.info("Created email={} id={}", userAdded.getEmail(), userAdded.getId());

		return userAdded;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}
}
