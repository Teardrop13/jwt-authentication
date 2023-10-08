package pl.teardrop.authentication.user.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.teardrop.authentication.user.domain.Password;
import pl.teardrop.authentication.user.domain.PasswordEncrypted;

@Service
@AllArgsConstructor
public class PasswordEncryptor {

	private final PasswordEncoder passwordEncoder;

	public PasswordEncrypted encrypt(Password password) {
		return new PasswordEncrypted(passwordEncoder.encode(password.getValue()));
	}
}
