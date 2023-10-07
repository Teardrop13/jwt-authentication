package pl.teardrop.authentication.user.domain;

import com.google.common.base.Strings;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.teardrop.authentication.user.exception.InvalidEmailException;

import java.util.regex.Pattern;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Email {

	public static final String JWT_FIELD_NAME = "email";

	@Getter(value = AccessLevel.NONE)
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(\\S+)$");

	private String value;

	public Email(String value) throws InvalidEmailException {
		if (Strings.isNullOrEmpty(value) || !EMAIL_PATTERN.matcher(value).matches()) {
			throw new InvalidEmailException();
		}
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
