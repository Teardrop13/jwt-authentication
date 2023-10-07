package pl.teardrop.authentication.user.service;

import lombok.NonNull;
import pl.teardrop.authentication.user.domain.Email;
import pl.teardrop.authentication.user.domain.Password;
import pl.teardrop.authentication.user.domain.User;
import pl.teardrop.authentication.user.domain.UserId;

import java.util.Optional;

public interface UserService {

	Optional<User> getById(UserId userId);

	boolean checkIfUserExists(@NonNull Email email);

	User loadUserByEmail(Email email);

	User create(Email email, Password password);

	User save(User user);
}
