package pl.teardrop.authentication.auth.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.teardrop.authentication.auth.dto.LoginRequest;
import pl.teardrop.authentication.auth.dto.LoginResponse;
import pl.teardrop.authentication.auth.dto.RegisterRequest;
import pl.teardrop.authentication.jwt.domain.Jwt;
import pl.teardrop.authentication.jwt.domain.RefreshToken;
import pl.teardrop.authentication.jwt.domain.RefreshTokenResult;
import pl.teardrop.authentication.jwt.dto.RefreshJwtTokenRequest;
import pl.teardrop.authentication.jwt.dto.RefreshJwtTokenResponse;
import pl.teardrop.authentication.jwt.exception.InvalidRefreshTokenException;
import pl.teardrop.authentication.jwt.service.JwtGenerator;
import pl.teardrop.authentication.jwt.service.RefreshJwtService;
import pl.teardrop.authentication.jwt.service.RefreshTokenDeleting;
import pl.teardrop.authentication.jwt.service.RefreshTokenGenerator;
import pl.teardrop.authentication.user.domain.CustomUserDetails;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.Password;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;
import pl.teardrop.authentication.user.exception.InvalidEmailException;
import pl.teardrop.authentication.user.exception.InvalidPasswordException;
import pl.teardrop.authentication.user.service.UserService;
import pl.teardrop.authentication.user.service.UserUtils;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

	private final UserService userService;
	private final JwtGenerator jwtGenerator;
	private final RefreshJwtService refreshJwtService;
	private final RefreshTokenGenerator refreshTokenGenerator;
	private final RefreshTokenDeleting refreshTokenDeleting;
	private final AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
		final Email email;
		try {
			email = new Email(request.getEmail());
		} catch (InvalidEmailException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid email provided");
		}

		final Password password;
		try {
			password = new Password(request.getPassword());
		} catch (InvalidPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password provided");
		}

		if (!userService.checkIfUserExists(email)) {
			final User user = userService.create(email, password);
			log.info("Successful user registration email={} id={}", user.getEmail(), user.getId());
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User with provided email or username already exists.");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

			final User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

			Jwt jwt = jwtGenerator.generate(user);
			RefreshToken refreshToken = refreshTokenGenerator.generate(user);

			log.info("user email={} authenticated", user.getEmail());

			return ResponseEntity.ok(new LoginResponse(jwt.getValue(), refreshToken.getValue()));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Object> logout(@RequestHeader Map<String, String> headers) {
		UserId userId = UserUtils.currentUserId();
		refreshTokenDeleting.delete(userId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/refresh-jwt")
	public ResponseEntity<Object> refreshJwtToken(@Valid @RequestBody RefreshJwtTokenRequest refreshRequest) {
		RefreshToken refreshToken = new RefreshToken(refreshRequest.getRefreshToken());

		try {
			RefreshTokenResult refreshResult = refreshJwtService.refreshJwtToken(refreshToken);
			return ResponseEntity.ok(new RefreshJwtTokenResponse(refreshResult.getJwt().getValue(),
																 refreshResult.getRefreshToken().getValue()));
		} catch (InvalidRefreshTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
		}
	}
}
