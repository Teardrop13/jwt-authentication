package pl.teardrop.authentication.user.domain;

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

@Entity
@Table(name = "A_USER")
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "EMAIL", nullable = false))
	private Email email;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "PASSWORD", nullable = false))
	private PasswordEncrypted passwordEncrypted;

	public UserId userId() {
		return new UserId(getId());
	}
}
