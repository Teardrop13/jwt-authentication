package pl.teardrop.authentication.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.teardrop.authentication.user.exception.InvalidPasswordException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PasswordTest {

	@Test
	void passwordConstructor_whenPasswordNull() {
		assertThrows(InvalidPasswordException.class, () -> new Password(null));
	}

	@Test
	void passwordConstructor_whenPasswordInvalid() {
		assertThrows(InvalidPasswordException.class, () -> new Password("aa"));
	}

	@Test
	void passwordConstructor_whenPasswordCorrect() {
		assertDoesNotThrow(() -> new Password("aaaaaa"));
	}
}