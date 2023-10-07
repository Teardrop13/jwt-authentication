package pl.teardrop.authentication.jwt.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class RefreshJwtTokenRequest {

	@NotBlank
	private String refreshToken;
}
