package pl.teardrop.authentication.jwt.exception;

public class ExpiredJwtTokenException extends Exception {

	public ExpiredJwtTokenException(Throwable cause) {
		super(cause);
	}
}
