package pl.teardrop.authentication.user.domain;

import com.google.common.base.Strings;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.teardrop.authentication.user.exception.InvalidPasswordException;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Password {

	private String value;

	public Password(String value) throws InvalidPasswordException {
		if (Strings.isNullOrEmpty(value) || value.length() < 6) {
			throw new InvalidPasswordException();
		}
		this.value = value;
	}

}
