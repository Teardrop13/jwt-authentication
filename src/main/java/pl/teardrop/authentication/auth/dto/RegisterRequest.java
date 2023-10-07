package pl.teardrop.authentication.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegisterRequest {

	private String email;
	private String password;
}
