package pl.teardrop.authentication.jwt.service;

import com.google.common.base.Strings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.teardrop.authentication.jwt.exception.FailedRetrievingAuthorizationToken;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenBearerUtil {

	public static String getJwtToken(String bearer) throws FailedRetrievingAuthorizationToken {
		if (Strings.isNullOrEmpty(bearer) || !bearer.startsWith("Bearer ")) {
			throw new FailedRetrievingAuthorizationToken();
		}

		return bearer.substring(7);
	}
}
