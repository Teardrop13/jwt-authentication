package pl.teardrop.authentication.user.domain;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class UserId {

	public static final String JWT_FIELD_NAME = "userId";

	private Long id;

	public UserId(Long id) {
		this.id = Objects.requireNonNull(id);
	}
}
