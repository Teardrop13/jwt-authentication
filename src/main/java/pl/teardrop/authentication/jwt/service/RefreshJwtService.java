package pl.teardrop.authentication.jwt.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.teardrop.authentication.jwt.exception.InvalidRefreshTokenException;
import pl.teardrop.authentication.user.exception.UserNotFoundException;
import pl.teardrop.authentication.jwt.domain.RefreshToken;
import pl.teardrop.authentication.jwt.domain.RefreshTokenInfo;
import pl.teardrop.authentication.jwt.repository.RefreshTokenInfoRepository;
import pl.teardrop.authentication.jwt.domain.RefreshTokenResult;
import pl.teardrop.authentication.jwt.domain.Jwt;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.service.UserService;

@Service
@AllArgsConstructor
public class RefreshJwtService {

	private final RefreshTokenInfoRepository refreshTokenInfoRepository;
	private final RefreshTokenGenerator refreshTokenGenerator;
	private final UserService userService;
	private final JwtGenerator jwtGenerator;

	@Transactional
	public RefreshTokenResult refreshJwtToken(RefreshToken refreshToken) throws InvalidRefreshTokenException {
		RefreshTokenInfo refreshTokenInfo = refreshTokenInfoRepository.findByRefreshToken(refreshToken)
				.orElseThrow(() -> new InvalidRefreshTokenException());

		User user = userService.getById(refreshTokenInfo.getUserId())
				.orElseThrow(() -> new UserNotFoundException());

		refreshTokenInfoRepository.delete(refreshTokenInfo);

		Jwt jwt = jwtGenerator.generate(user);
		RefreshToken newRefreshToken = refreshTokenGenerator.generate(user);

		return new RefreshTokenResult(jwt, newRefreshToken);
	}
}
