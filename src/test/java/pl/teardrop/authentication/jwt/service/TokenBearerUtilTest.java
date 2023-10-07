package pl.teardrop.authentication.jwt.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.teardrop.authentication.jwt.exception.FailedRetrievingAuthorizationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TokenBearerUtilTest {

	@Test
	void getJwtToken_whenBearerIsCorrect() throws FailedRetrievingAuthorizationToken {
		final String token = "1234";
		final String bearer = "Bearer " + token;

		String resultToken = TokenBearerUtil.getJwtToken(bearer);

		assertEquals(token, resultToken);
	}

	@Test
	void getJwtToken_whenBearerIsInCorrect() {
		final String bearer = "1234";

		assertThrows(FailedRetrievingAuthorizationToken.class, () -> TokenBearerUtil.getJwtToken(bearer));
	}

	@Test
	void getJwtToken_whenBearerIsNull() {
		final String bearer = null;

		assertThrows(FailedRetrievingAuthorizationToken.class, () -> TokenBearerUtil.getJwtToken(bearer));
	}

}