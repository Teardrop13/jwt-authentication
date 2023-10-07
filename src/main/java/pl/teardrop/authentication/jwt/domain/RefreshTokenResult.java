package pl.teardrop.authentication.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshTokenResult {

	private Jwt jwt;
	private RefreshToken refreshToken;
}
