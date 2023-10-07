package pl.teardrop.authentication.jwt.service;

import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.teardrop.authentication.jwt.exception.ExpiredJwtTokenException;
import pl.teardrop.authentication.user.exception.InvalidEmailException;
import pl.teardrop.authentication.jwt.exception.InvalidJwtTokenException;
import pl.teardrop.authentication.jwt.domain.Jwt;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtGeneratorDecoderTest {

	@Mock
	private SecretKeyProvider secretKeyProvider;
	@Mock
	private JwtExpirationTimeProvider jwtExpirationTimeProvider;
	private JwtGenerator jwtGenerator;
	private JwtDecoder jwtDecoder;
	private User user;

	@BeforeEach
	void setUp() throws InvalidEmailException {
		jwtGenerator = new JwtGenerator(secretKeyProvider, jwtExpirationTimeProvider);
		jwtDecoder = new JwtDecoder(secretKeyProvider);

		user = User.builder()
				.id(1L)
				.email(new Email("a@a.a"))
				.build();

		when(secretKeyProvider.provide()).thenReturn(new SecretKeySpec("jlkasjdflkajsdlkfjasdkfjasld8741987431897324012734981".getBytes(), SignatureAlgorithm.HS256.getJcaName()));
	}

	@Test
	void generateAndDecode_whenJwtNotExpired() throws InvalidJwtTokenException, ExpiredJwtTokenException {
		when(jwtExpirationTimeProvider.provide()).thenReturn(new Date(System.currentTimeMillis() + (10 * 60 * 1000)));

		Jwt jwt = jwtGenerator.generate(user);
		UserId userId = jwtDecoder.decode(jwt.getValue());

		assertEquals(userId, user.userId());
	}

	@Test
	void generateAndDecode_throwsException_whenJwtExpired() {
		when(jwtExpirationTimeProvider.provide()).thenReturn(new Date(System.currentTimeMillis() - (10 * 60 * 1000)));

		Jwt jwt = jwtGenerator.generate(user);

		assertThrows(ExpiredJwtTokenException.class, () -> jwtDecoder.decode(jwt.getValue()));
	}

	@Test
	void generateAndDecode_throwsException_whenJwtInvalid() {
		Jwt jwt = new Jwt("aaaaaaa");

		assertThrows(InvalidJwtTokenException.class, () -> jwtDecoder.decode(jwt.getValue()));
	}

	@Test
	void generateAndDecode_throwsException_whenJwtModified() {
		when(jwtExpirationTimeProvider.provide()).thenReturn(new Date(System.currentTimeMillis() + (10 * 60 * 1000)));

		final Jwt jwt = jwtGenerator.generate(user);

		Base64.Decoder decoder = Base64.getUrlDecoder();
		Base64.Encoder encoder = Base64.getUrlEncoder();

		String[] splitToken = jwt.getValue().split("\\.");

		String header = splitToken[0];
		String payload = splitToken[1];
		String signature = splitToken[2];

		String payloadString = new String(decoder.decode(payload));

		String changedPayloadString = payloadString.replace("\"userId\":1", "\"userId\":2");

		String changedPayload = new String(encoder.encode(changedPayloadString.getBytes(StandardCharsets.UTF_8)))
				.replace("=", "");

		final Jwt modifiedJwt = new Jwt(header + "." + changedPayload + "." + signature);

		assertThrows(InvalidJwtTokenException.class, () -> jwtDecoder.decode(modifiedJwt.getValue()));
	}

}