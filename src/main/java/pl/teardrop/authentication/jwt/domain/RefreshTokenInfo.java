package pl.teardrop.authentication.jwt.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.teardrop.authentication.user.domain.UserId;

import java.time.LocalDateTime;

@Entity
@Table(name = "A_REFRESH_TOKEN_INFO")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class RefreshTokenInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Embedded
	@AttributeOverride(name = "id", column = @Column(name = "USER_ID", nullable = false))
	private UserId userId;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "REFRESH_TOKEN", nullable = false))
	private RefreshToken refreshToken;

	@Column(name = "LAST_ACTIVITY")
	private LocalDateTime lastActivity;
}
