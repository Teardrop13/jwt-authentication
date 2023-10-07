package pl.teardrop.authentication.jwt.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.teardrop.authentication.jwt.repository.RefreshTokenInfoRepository;
import pl.teardrop.authentication.user.domain.UserId;

@Service
@AllArgsConstructor
@Slf4j
public class RefreshTokenDeleting {

	private final RefreshTokenInfoRepository refreshTokenInfoRepository;

	public void delete(UserId userId) {
		refreshTokenInfoRepository.findByUserId(userId)
				.forEach(refreshTokenInfoRepository::delete);
		log.info("Removed all refresh tokens for userId=%d".formatted(userId.getId()));
	}
}
