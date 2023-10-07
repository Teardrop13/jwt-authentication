package pl.teardrop.authentication.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.teardrop.authentication.user.exception.InvalidEmailException;
import pl.teardrop.authentication.user.exception.UserNotFoundException;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncryptor passwordEncryptor;
	private UserService userService;

	@BeforeEach
	void setUp() {
		userService = new DefaultUserService(userRepository, passwordEncryptor);
	}

	@Test
	void checkIfUserExists_returnTrueIfExist() throws InvalidEmailException {
		Email email = new Email("user@gmail.com");

		UserService userServiceMock = spy(userService);

		doReturn(new User()).when(userServiceMock).loadUserByEmail(email);

		boolean exists = userServiceMock.checkIfUserExists(email);

		assertTrue(exists);
	}

	@Test
	void checkIfUserExists_returnFalseIfUserDoesNotExist() throws InvalidEmailException {
		Email email = new Email("user@gmail.com");

		UserService userServiceMock = spy(userService);

		doThrow(UserNotFoundException.class).when(userServiceMock).loadUserByEmail(email);

		boolean exists = userServiceMock.checkIfUserExists(email);
		assertFalse(exists);
	}

	@Test
	void loadUserByEmail_whenUserExists() throws InvalidEmailException {
		Email email = new Email("user@gmail.com");
		User user = User.builder().email(email).build();

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

		User returnedUser = userService.loadUserByEmail(email);
		assertEquals(returnedUser, user);
	}

	@Test
	void loadUserByEmail_whenUserExistsDoesNotExist() throws InvalidEmailException {
		Email email = new Email("user@gmail.com");

		when(userRepository.findUserByEmail(email)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.loadUserByEmail(email));
	}

}