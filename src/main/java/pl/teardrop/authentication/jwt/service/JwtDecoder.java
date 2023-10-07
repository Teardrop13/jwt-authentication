package pl.teardrop.authentication.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.teardrop.authentication.jwt.exception.ExpiredJwtTokenException;
import pl.teardrop.authentication.jwt.exception.InvalidJwtTokenException;
import pl.teardrop.authentication.user.domain.UserId;

@Service
@AllArgsConstructor
public class JwtDecoder {

	private SecretKeyProvider secretKeyProvider;

	public UserId decode(String token) throws InvalidJwtTokenException, ExpiredJwtTokenException {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(secretKeyProvider.provide().getEncoded()).build();
		try {
			Jws<Claims> claims = parser.parseClaimsJws(token);
			return new UserId(((Integer) claims.getBody().get(UserId.JWT_FIELD_NAME)).longValue());
		} catch (ExpiredJwtException e) {
			throw new ExpiredJwtTokenException(e);
		} catch (JwtException e) {
			throw new InvalidJwtTokenException(e);
		}
	}
}
