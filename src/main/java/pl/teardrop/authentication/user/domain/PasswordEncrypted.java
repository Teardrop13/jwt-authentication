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
public class PasswordEncrypted {

	private String value;

	public PasswordEncrypted(String value) {
		this.value = Objects.requireNonNull(value);
	}
}
