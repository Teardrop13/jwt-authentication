package pl.teardrop.authentication.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.teardrop.authentication.user.exception.InvalidEmailException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EmailTest {

	@Test
	void emailConstructor_whenEmailNull() {
		assertThrows(InvalidEmailException.class, () -> new Email(null));
	}

	@Test
	void emailConstructor_whenEmailInvalid() {
		assertThrows(InvalidEmailException.class, () -> new Email("aa"));
	}

	@Test
	void emailConstructor_whenEmailCorrect() {
		assertDoesNotThrow(() -> new Email("a@a.a"));
	}
}