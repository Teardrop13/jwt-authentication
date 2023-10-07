package pl.teardrop.authentication.jwt.domain;

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
public class RefreshToken {

	private String value;

	public RefreshToken(String value) {
		this.value = Objects.requireNonNull(value);
	}
}
