package pl.teardrop.authentication.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginRequest {

	private String email;
	private String password;
}
