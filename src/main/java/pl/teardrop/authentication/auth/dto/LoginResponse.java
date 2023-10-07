package pl.teardrop.authentication.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {

	private String jwt;
	private String refreshToken;
}
