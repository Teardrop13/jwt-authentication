package pl.teardrop.authentication.jwt.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.teardrop.authentication.jwt.exception.ExpiredJwtTokenException;
import pl.teardrop.authentication.jwt.exception.FailedRetrievingAuthorizationToken;
import pl.teardrop.authentication.jwt.exception.InvalidJwtTokenException;
import pl.teardrop.authentication.user.domain.CustomUserDetails;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;
import pl.teardrop.authentication.user.service.UserService;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	@Lazy
	private final UserService userService;
	private final JwtDecoder jwtDecoder;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {

		final String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
		log.debug("Token: {}", bearer);

		String jwt;
		try {
			jwt = TokenBearerUtil.getJwtToken(bearer);
		} catch (FailedRetrievingAuthorizationToken e) {
			log.debug("Invalid token received: {}", bearer);
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			filterChain.doFilter(request, response);
			return;
		}

		UserId userId;
		try {
			userId = jwtDecoder.decode(jwt);
		} catch (InvalidJwtTokenException | ExpiredJwtTokenException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			filterChain.doFilter(request, response);
			return;
		}

		Optional<User> userOpt = userService.getById(userId);
		if (userOpt.isEmpty()) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			filterChain.doFilter(request, response);
			return;
		}
		User user = userOpt.get();

		CustomUserDetails userDetails = new CustomUserDetails(user);

		final UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
				user,
				null,
				userDetails.getAuthorities()
		);

		SecurityContextHolder.getContext().setAuthentication(auth);
		log.debug("User email={}, id={} authenticated", user.getEmail(), user.getId());
		filterChain.doFilter(request, response);
	}
}
