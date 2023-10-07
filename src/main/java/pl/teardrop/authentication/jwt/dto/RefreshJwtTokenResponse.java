package pl.teardrop.authentication.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshJwtTokenResponse {

	private String jwt;
	private String refreshToken;
}
