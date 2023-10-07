package pl.teardrop.authentication.jwt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtExpirationTimeProvider {

	@Value("${JWT_EXPIRATION_TIME}")
	private long expirationTimeSeconds;

	public Date provide() {
		return new Date(System.currentTimeMillis() + (expirationTimeSeconds * 1000));
	}
}
