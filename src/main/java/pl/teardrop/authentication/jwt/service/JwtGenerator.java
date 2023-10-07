package pl.teardrop.authentication.jwt.service;

import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.teardrop.authentication.jwt.domain.Jwt;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class JwtGenerator {

	private SecretKeyProvider secretKeyProvider;
	private JwtExpirationTimeProvider jwtExpirationTimeProvider;

	public Jwt generate(User user) {
		Date expiration = jwtExpirationTimeProvider.provide();
		SecretKey secretKey = secretKeyProvider.provide();

		String jwt = Jwts.builder()
				.claim(UserId.JWT_FIELD_NAME, user.userId().getId())
				.claim(Email.JWT_FIELD_NAME, user.getEmail().getValue())
				.setExpiration(expiration)
				.setIssuedAt(new Date())
				.signWith(secretKey)
				.compact();

		return new Jwt(jwt);
	}
}
