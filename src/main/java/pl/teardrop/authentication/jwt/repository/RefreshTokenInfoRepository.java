package pl.teardrop.authentication.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.teardrop.authentication.jwt.domain.RefreshToken;
import pl.teardrop.authentication.jwt.domain.RefreshTokenInfo;
import pl.teardrop.authentication.user.domain.UserId;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenInfoRepository extends CrudRepository<RefreshTokenInfo, Long> {

	Optional<RefreshTokenInfo> findByRefreshToken(RefreshToken refreshToken);

	List<RefreshTokenInfo> findByUserId(UserId userId);
}
