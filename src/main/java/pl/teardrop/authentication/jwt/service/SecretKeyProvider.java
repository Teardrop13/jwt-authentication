package pl.teardrop.authentication.jwt.service;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class SecretKeyProvider {

	@Value("${SECRET_KEY}")
	private String secretKeyText;

	public SecretKey provide() {
		return new SecretKeySpec(secretKeyText.getBytes(), SignatureAlgorithm.HS256.getJcaName());
	}
}
