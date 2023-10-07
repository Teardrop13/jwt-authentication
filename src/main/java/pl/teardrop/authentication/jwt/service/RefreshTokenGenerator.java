package pl.teardrop.authentication.jwt.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.teardrop.authentication.jwt.domain.RefreshToken;
import pl.teardrop.authentication.jwt.domain.RefreshTokenInfo;
import pl.teardrop.authentication.jwt.repository.RefreshTokenInfoRepository;
import pl.teardrop.authentication.user.domain.User;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenGenerator {

	private final RefreshTokenInfoRepository refreshTokenInfoRepository;

	public RefreshToken generate(User user) {
		final String randomUUIDString = UUID.randomUUID().toString();
		final byte[] uuidBytes = randomUUIDString.getBytes(StandardCharsets.UTF_8);
		final byte[] base64EncodedBytes = Base64.getEncoder().encode(uuidBytes);
		final RefreshToken refreshToken = new RefreshToken(new String(base64EncodedBytes));

		RefreshTokenInfo refreshTokenInfo = RefreshTokenInfo.builder()
				.userId(user.userId())
				.refreshToken(refreshToken)
				.lastActivity(LocalDateTime.now())
				.build();

		refreshTokenInfoRepository.save(refreshTokenInfo);

		return refreshToken;
	}

}
